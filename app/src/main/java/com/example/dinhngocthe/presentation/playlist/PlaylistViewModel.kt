package com.example.dinhngocthe.presentation.playlist

import android.app.Application
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.LinkAnnotation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.dinhngocthe.R
import com.example.dinhngocthe.model.Song
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

class PlaylistViewModel(private val context: Application) : ViewModel() {
    private val _state = MutableStateFlow(PlaylistState(songs = List(3) { getAllSongs(context)}.flatten()))
    val state = _state.asStateFlow()

    fun processIntent(intent: PlaylistIntent) {
        when (intent) {
            PlaylistIntent.ToggleDisplayMode -> _state.update {
                it.copy(isListMode = !it.isListMode, expandedMenuIndex = -1)
            }

            is PlaylistIntent.ShowMenu -> _state.update {
                it.copy(expandedMenuIndex = intent.index)
            }

            PlaylistIntent.DismissMenu -> _state.update {
                it.copy(expandedMenuIndex = -1)
            }

            is PlaylistIntent.RemoveSong -> _state.update {
                val newSongs = it.songs.toMutableList().apply { removeAt(intent.index) }
                it.copy(songs = newSongs, expandedMenuIndex = -1)
            }

            is PlaylistIntent.ShareSong -> {
                _state.update { it.copy(expandedMenuIndex = -1) }
            }
        }
    }

    private fun getAllSongs(context: Application): List<Song> {
        val contentResolver = context.contentResolver
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION
        )
        val selection = MediaStore.Audio.Media.IS_MUSIC

        val songs = mutableListOf<Song>()
        contentResolver.query(uri, projection, selection, null, null)?.use { it ->
            val idCol = it.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val titleCol = it.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val artistCol = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val durationCol = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)

            while (it.moveToNext()) {
                val id = it.getLong(idCol)
                val title = it.getString(titleCol) ?: ""
                val artist = it.getString(artistCol) ?: ""
                val duration = it.getLong(durationCol)

                val contentUri = ContentUris.withAppendedId(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id
                )

                // Lấy ảnh từ metadata của bài hát
                val embeddedCoverUri: Uri? = try {
                    val mmr = MediaMetadataRetriever()
                    mmr.setDataSource(context, contentUri)
                    mmr.embeddedPicture?.let { bytes ->
                        val file = File(context.cacheDir, "$id")
                        file.writeBytes(bytes)
                        Uri.fromFile(file)
                    }.also {
                        mmr.release()
                    }
                } catch (_: Exception) {
                    null
                }

                songs.add(
                    Song(
                        id = id.toInt(),
                        name = title.dropLast(4),
                        singers = artist,
                        duration = duration,
                        coverArt = embeddedCoverUri
                    )
                )
            }
        }
        return songs
    }

    class PlaylistViewModelFactory(private val context: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PlaylistViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PlaylistViewModel(context) as T
            }
            throw IllegalArgumentException("Unable to construct PlaylistViewModelFactory")
        }
    }

}
