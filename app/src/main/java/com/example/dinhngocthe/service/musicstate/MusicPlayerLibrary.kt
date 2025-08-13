package com.example.dinhngocthe.service.musicstate

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import com.example.dinhngocthe.data.local.entities.Song
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

object MusicPlayerLibrary {
    private var mediaPlayer: MediaPlayer? = null
    private var updateProgressJob: Job? = null

    fun playMusic(song: Song, currentPlaySourceName: String, context: Context) {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer().apply {
            setDataSource(context, song.audioUri!!)
            setOnPreparedListener {
                start()
                startUpdatingProgress()
            }
            setOnCompletionListener {
                MusicStateHolder.updateIsPlayingState(false)
            }
            prepareAsync()
        }

        val musicState = MusicState(
            songId = song.songId,
            currentPlaySourceName = currentPlaySourceName,
            coverArtUri = song.coverArtUri,
            singer = song.singer,
            progress = 0f,
            songName = song.songName,
            duration = song.duration,
            isPlaying = true,
            isActive = true,
            isShuffle = false,
            isRepeat = false,
            enableNext = false,
            enablePrevious = false,
            enableRepeat = false,
            enableShuffle = false
        )
        MusicStateHolder.updateState(musicState)
    }

    fun playPauseMusic() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
                MusicStateHolder.updateIsPlayingState(false)
            } else {
                it.start()
                MusicStateHolder.updateIsPlayingState(true)
                startUpdatingProgress()
            }
        }
    }

    fun stopMusic() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        MusicStateHolder.closePlayMusic()
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

    fun isActive(): Boolean {
        return mediaPlayer != null
    }

    fun updateProgress(progress: Float) {
        mediaPlayer?.let { mediaPlayer ->
            val duration = mediaPlayer.duration
            val newPosition = (duration * progress).toInt()
            mediaPlayer.seekTo(newPosition)
            if (!mediaPlayer.isPlaying) {
                mediaPlayer.start()
                MusicStateHolder.updateIsPlayingState(true)
            }
            startUpdatingProgress()
        }
    }

    fun stopUpdateProgress() {
        updateProgressJob?.cancel()
    }
}