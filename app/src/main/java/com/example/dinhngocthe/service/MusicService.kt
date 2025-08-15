package com.example.dinhngocthe.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.media.app.NotificationCompat.MediaStyle
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import androidx.core.content.ContextCompat
import coil.ImageLoader
import coil.request.ImageRequest
import com.example.dinhngocthe.MainActivity
import com.example.dinhngocthe.R
import com.example.dinhngocthe.data.local.LocalDatabase
import com.example.dinhngocthe.data.local.dao.SongDao
import com.example.dinhngocthe.data.local.entities.Song
import com.example.dinhngocthe.service.musicstate.MusicState
import com.example.dinhngocthe.service.musicstate.MusicStateHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.io.InputStream
import androidx.core.graphics.createBitmap
import com.example.dinhngocthe.service.musicstate.MusicCloseSignal

class MusicService : Service() {
    private lateinit var mediaSession: MediaSessionCompat
    private var mediaPlayer: MediaPlayer? = null

    private lateinit var songDao: SongDao
    private var playlist: List<Song> = emptyList()
    private var playlistBackUp: List<Song> = emptyList()
    private var currentTrackIndex = 0
    private var updateProgressJob: Job? = null
    private var isShuffle: Boolean = false
    private var isRepeat: Boolean = false

    companion object {
        const val CHANNEL_ID = "music_channel"
        const val NOTIFICATION_ID = 1

        const val ACTION_PLAY_PAUSE = "ACTION_PLAY_PAUSE"
        const val ACTION_NEXT = "ACTION_NEXT"
        const val ACTION_PREVIOUS = "ACTION_PREVIOUS"
        const val ACTION_CLOSE = "ACTION_CLOSE"
        const val ACTION_START = "ACTION_START"
        const val ACTION_SEEK_TO = "ACTION_SEEK_TO"
        const val ACTION_STOP_UPDATE_PROGRESS = "ACTION_STOP_UPDATE_PROGRESS"
        const val ACTION_SHUFFLE = "ACTION_SHUFFLE"
        const val ACTION_REPEAT = "ACTION_REPEAT"
    }

    override fun onCreate() {
        super.onCreate()
        mediaSession = MediaSessionCompat(this, "MusicService")
        songDao = LocalDatabase.getInstance(application).songDao()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Music Playback",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_PLAY_PAUSE -> {
                togglePlayPause()
            }
            ACTION_NEXT -> {
                nextTrack()
            }
            ACTION_PREVIOUS -> {
                previousTrack()
            }
            ACTION_CLOSE -> {
                MusicStateHolder.closePlayMusic()
                stopForeground(true)
                stopSelf()
                MusicCloseSignal.notifyClosed()
            }
            ACTION_START -> {
                start(intent)
            }
            ACTION_SEEK_TO -> {
                seekTo(intent)
            }
            ACTION_STOP_UPDATE_PROGRESS -> {
                Log.e("MusicService", "Stop update progress")
                updateProgressJob?.cancel()
            }
            ACTION_SHUFFLE -> {
                handleShuffle()
            }
            ACTION_REPEAT -> {
                handleRepeat()
            }
        }
        return START_STICKY
    }

    private fun handleRepeat() {
        isRepeat = !isRepeat
        val state = MusicStateHolder.state.value
        MusicStateHolder.updateState(
            state.copy(
                isRepeat = isRepeat
            )
        )
    }

    private fun handleShuffle() {
        if (!isShuffle) {
            shuffle()
        } else {
            unShuffle()
        }
    }

    private fun shuffle() {
        val currentSong = playlist[currentTrackIndex]
        val shuffleList = playlist.toMutableList()
        shuffleList.remove(currentSong)
        shuffleList.shuffle()
        shuffleList.add(0, currentSong)
        playlist = shuffleList
        currentTrackIndex = 0
        val state = MusicStateHolder.state.value
        MusicStateHolder.updateState(
            state.copy(
                isShuffle = true
            )
        )
        isShuffle = true
        updateNotification()
    }

    private fun unShuffle() {
        val currentSong = playlist[currentTrackIndex]
        playlist = playlistBackUp.toList()
        currentTrackIndex = playlist.indexOf(currentSong)
        val state = MusicStateHolder.state.value
        MusicStateHolder.updateState(
            state.copy(
                isShuffle = false
            )
        )
        isShuffle = false
        updateNotification()
    }

    private fun seekTo(intent: Intent) {
        val newProgress = intent.getFloatExtra("PROGRESS", 0f)
        mediaPlayer?.let { player ->
            val duration = player.duration
            val newPosition = (duration * newProgress).toInt()
            player.seekTo(newPosition)
            //val currentState = MusicStateHolder.state.value
            //MusicStateHolder.updateState(currentState.copy(progress = progress))
            if (!player.isPlaying) {
                player.start()
                MusicStateHolder.updateIsPlayingState(true)
            }
            startUpdatingProgress()
            updateNotification()
        }
    }

    private fun start(intent: Intent) {
        val songId = intent.getLongExtra("SONG_ID", -1)
        val sourceName = intent.getStringExtra("SOURCE_NAME") ?: ""
        val songIds = intent.getLongArrayExtra("SONG_IDS")  ?: longArrayOf()

        CoroutineScope(Dispatchers.IO).launch {
            playlist = songDao.getSongsBySongId(songIds.toList())
            playlistBackUp = playlist.toList()
            updateCurrentTrackIndex(songId)
            prepareAndStart()
            startForeground(NOTIFICATION_ID, buildNotification())
            val musicState = MusicState(
                songId = playlist[currentTrackIndex].songId,
                currentPlaySourceName = sourceName,
                coverArtUri = playlist[currentTrackIndex].coverArtUri,
                singer = playlist[currentTrackIndex].singer,
                progress = 0f,
                songName = playlist[currentTrackIndex].songName,
                duration = playlist[currentTrackIndex].duration,
                isPlaying = true,
                isActive = true,
                isShuffle = false,
                isRepeat = false,
                enableNext = true,
                enableShuffle = true,
                enableRepeat = true,
                enablePrevious = true
            )
            MusicStateHolder.updateState(musicState)
        }
    }

    private fun startUpdatingProgress() {
        updateProgressJob?.cancel()
        updateProgressJob = CoroutineScope(Dispatchers.Main).launch {
            try {
                while (isActive && mediaPlayer?.isPlaying == true) {
                    val position = mediaPlayer?.currentPosition ?: 0
                    val duration = mediaPlayer?.duration ?: 0
                    val progress = if (duration > 0) position.toFloat() / duration else 0f

                    val currentMusicState = MusicStateHolder.state.value
                    MusicStateHolder.updateState(
                        currentMusicState.copy(
                            progress = progress
                        )
                    )
                    delay(500)
                }
            } catch(e: Exception) {
                Log.e("MusicService", e.printStackTrace().toString())
            }
        }
    }

    private fun prepareAndStart() {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer().apply {
            setDataSource(applicationContext, playlist[currentTrackIndex].audioUri!!)
            setOnPreparedListener {
                start()
                updateNotification()
                startUpdatingProgress()
            }
            setOnCompletionListener {
                if (isRepeat == false && currentTrackIndex == playlist.size - 1) {
                    val state = MusicStateHolder.state.value
                    MusicStateHolder.updateState(
                        state.copy(
                            isPlaying = false
                        )
                    )
                    updateNotification()
                    return@setOnCompletionListener
                } else {
                    nextTrack()
                }
            }
            prepareAsync()
        }
    }

    private fun updateCurrentTrackIndex(songId: Long) {
        val index = playlist.indexOfFirst { it.songId == songId }
        currentTrackIndex = if (index != -1) index else 0
    }

    private fun togglePlayPause() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
                MusicStateHolder.updateIsPlayingState(false)
            } else {
                it.start()
                MusicStateHolder.updateIsPlayingState(true)
                startUpdatingProgress()
            }
            updateNotification()
        }
    }

    private fun nextTrack() {
        if (isRepeat == false && currentTrackIndex == playlist.size - 1) {
            return
        }
        currentTrackIndex = (currentTrackIndex + 1) % playlist.size
        prepareAndStart()
        val song = playlist[currentTrackIndex]
        val state = MusicStateHolder.state.value
        MusicStateHolder.updateState(
            state.copy(
                songId = song.songId,
                singer = song.singer,
                songName = song.songName,
                duration = song.duration,
                coverArtUri = song.coverArtUri,
                isPlaying = true
            )
        )
    }

    private fun previousTrack() {
        if (isRepeat == false && currentTrackIndex == 0) {
            return
        }
        currentTrackIndex = if (currentTrackIndex - 1 < 0) playlist.size - 1 else currentTrackIndex - 1
        prepareAndStart()
        val song = playlist[currentTrackIndex]
        val state = MusicStateHolder.state.value
        MusicStateHolder.updateState(
            state.copy(
                songId = song.songId,
                singer = song.singer,
                songName = song.songName,
                duration = song.duration,
                coverArtUri = song.coverArtUri,
                isPlaying = true
            )
        )
    }

    private fun buildNotification(): Notification {
        val currentSong = if (playlist.isNotEmpty()) playlist[currentTrackIndex] else null

        val playPauseIntent = PendingIntent.getService(
            this, 0, Intent(this, MusicService::class.java).setAction(ACTION_PLAY_PAUSE),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val nextIntent = PendingIntent.getService(
            this, 1, Intent(this, MusicService::class.java).setAction(ACTION_NEXT),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val previousIntent = PendingIntent.getService(
            this, 2, Intent(this, MusicService::class.java).setAction(ACTION_PREVIOUS),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val closeIntent = PendingIntent.getService(
            this, 3, Intent(this, MusicService::class.java).setAction(ACTION_CLOSE),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val contentTitle = "Apero Music   ${currentTrackIndex + 1}/${playlist.size}"
        val contentText = currentSong?.let {
            "${currentSong.songName} - ${currentSong.singer}"
        }

        val coverArt = if (currentSong?.coverArtUri == null) {
            drawableResToBitmap(this, R.drawable.img_song_default)
        } else {
            loadBitmapFromUri(application, currentSong.coverArtUri)
        }

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("DESTINATION", "MUSIC_PLAYER")
        }
        val mainIntent = PendingIntent.getActivity(
            this, 4, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(contentTitle)
            .setContentText(contentText)
            .setSmallIcon(R.drawable.ic_logo)
            .setContentIntent(mainIntent)
            .addAction(R.drawable.ic_previous_music, "Previous", previousIntent)
            .addAction(
                if (mediaPlayer?.isPlaying == true) R.drawable.ic_pause else R.drawable.ic_play,
                if (mediaPlayer?.isPlaying == true) "Pause" else "Play",
                playPauseIntent
            )
            .addAction(R.drawable.ic_next_music, "Next", nextIntent)
            .addAction(R.drawable.ic_close_music, "Close", closeIntent)
            .setStyle(
                MediaStyle()
                    .setMediaSession(mediaSession.sessionToken)
            )
            .setLargeIcon(coverArt)
            .setOnlyAlertOnce(true)
            .build()
    }

    private fun updateNotification() {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, buildNotification())
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        mediaPlayer = null
        mediaSession.release()
        updateProgressJob?.cancel()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    fun loadBitmapFromUri(context: Context, uri: Uri): Bitmap {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val bitmap = inputStream.use { stream ->
                BitmapFactory.decodeStream(stream)
            }
            bitmap ?: drawableResToBitmap(context, R.drawable.img_song_default)
        } catch (e: Exception) {
            e.printStackTrace()
            drawableResToBitmap(context, R.drawable.img_song_default)
        }
    }

    fun drawableResToBitmap(context: Context, drawableRes: Int): Bitmap {
        val drawable = ContextCompat.getDrawable(context, drawableRes)!!
        val bitmap = createBitmap(
            drawable.intrinsicWidth.coerceAtLeast(1),
            drawable.intrinsicHeight.coerceAtLeast(1)
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }
}