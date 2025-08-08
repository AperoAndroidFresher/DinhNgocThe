package com.example.dinhngocthe.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.media.app.NotificationCompat.MediaStyle
import android.support.v4.media.session.MediaSessionCompat
import com.example.dinhngocthe.MainActivity
import com.example.dinhngocthe.R
import com.example.dinhngocthe.data.local.LocalDatabase
import com.example.dinhngocthe.data.local.dao.SongDao
import com.example.dinhngocthe.data.local.entities.Song
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MusicService : Service() {
    private lateinit var mediaSession: MediaSessionCompat
    private var mediaPlayer: MediaPlayer? = null

    private lateinit var songDao: SongDao
    private var playlist: List<Song> = emptyList()
    private var currentTrackIndex = 0

    companion object {
        const val CHANNEL_ID = "music_channel"
        const val NOTIFICATION_ID = 1

        const val ACTION_PLAY_PAUSE = "ACTION_PLAY_PAUSE"
        const val ACTION_NEXT = "ACTION_NEXT"
        const val ACTION_PREVIOUS = "ACTION_PREVIOUS"
        const val ACTION_CLOSE = "ACTION_CLOSE"
        const val ACTION_START = "ACTION_START"
    }

    override fun onCreate() {
        super.onCreate()
        mediaSession = MediaSessionCompat(this, "MusicService")
        songDao = LocalDatabase.getInstance(application).songDao()
        createNotificationChannel()
        CoroutineScope(Dispatchers.IO).launch {
            playlist = songDao.getAllSongsService()
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
                stopForeground(true)
                stopSelf()
            }
            ACTION_START -> {
                start(intent)
            }
        }
        return START_STICKY
    }

    private fun start(intent: Intent) {
        val songId = intent.getLongExtra("SONG_ID", -1)
        if (playlist.isEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                playlist = songDao.getAllSongsService()
                if (playlist.isNotEmpty()) {
                    updateCurrentTrackIndex(songId)
                    prepareAndStart()
                    startForeground(NOTIFICATION_ID, buildNotification())
                }
            }
        } else {
            updateCurrentTrackIndex(songId)
            prepareAndStart()
            startForeground(NOTIFICATION_ID, buildNotification())
        }
    }

    private fun prepareAndStart() {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer().apply {
            setDataSource(applicationContext, playlist[currentTrackIndex].audioUri!!)
            setOnPreparedListener {
                start()
                updateNotification()
            }
            setOnCompletionListener {
                nextTrack()
            }
            prepareAsync()
        }
    }

    private fun updateCurrentTrackIndex(songId: Long) {
        if (songId == -1L) {
            currentTrackIndex = 0
            return
        }
        val index = playlist.indexOfFirst { it.songId == songId }
        currentTrackIndex = if (index != -1) index else 0
    }

    private fun togglePlayPause() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
            } else {
                it.start()
            }
            updateNotification()
        }
    }

    private fun nextTrack() {
        if (playlist.isEmpty()) return
        currentTrackIndex = (currentTrackIndex + 1) % playlist.size
        prepareAndStart()
    }

    private fun previousTrack() {
        if (playlist.isEmpty()) return
        currentTrackIndex = if (currentTrackIndex - 1 < 0) playlist.size - 1 else currentTrackIndex - 1
        prepareAndStart()
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
        val mainIntent = PendingIntent.getActivity(
            this, 4, Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val trackInfo = if (currentSong != null) {
            "${currentSong.songName} - ${currentSong.singer}"
        } else "No song playing"

        val trackCountInfo = if (playlist.isNotEmpty()) {
            "${currentTrackIndex + 1}/${playlist.size}"
        } else ""

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("AperoMusic")
            .setContentText(trackInfo)
            .setSubText(trackCountInfo)
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
                    .setShowActionsInCompactView(0, 1, 2)
            )
            .setOnlyAlertOnce(true)
            .setOngoing(mediaPlayer?.isPlaying == true)
            .build()
    }

    private fun updateNotification() {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, buildNotification())
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

    override fun onDestroy() {
        mediaPlayer?.release()
        mediaPlayer = null
        mediaSession.release()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}