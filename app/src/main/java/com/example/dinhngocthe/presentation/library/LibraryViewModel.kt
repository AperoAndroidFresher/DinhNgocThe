package com.example.dinhngocthe.presentation.library

import android.app.Application
import android.content.ContentUris
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dinhngocthe.model.Song
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.File

class LibraryViewModel(private val context: Application) : ViewModel() {
    private val _state = MutableStateFlow<LibraryState>(LibraryState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<LibraryEvent>()
    init {
        loadLocalSongs()
    }

    fun processIntent(intent: LibraryIntent) {
        when(intent) {
            LibraryIntent.ToggleLocalButton -> _state.update { it.copy(displayMode = "local") }

            LibraryIntent.ToggleRemoteButton -> _state.update { it.copy(displayMode = "remote") }
            is LibraryIntent.AddToPlaylist -> {

            }
            LibraryIntent.DismissMenu -> {
                _state.update { it.copy(menuExpandedIndex = -1) }
            }
            is LibraryIntent.ShowMenu -> {
                _state.update { it.copy(menuExpandedIndex = intent.index) }
            }
        }
    }

    private fun loadLocalSongs() {
        try {
            _state.update { it.copy(isLoading = true) }

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
            if (songs.isEmpty()) {
                _state.update { it.copy(isLoading = false, error = "Không tìm thấy bài hát nào!") }
            } else _state.update { it.copy(localSongs = songs) }
        } catch (e: Exception) {
            Log.e("LibraryViewModel", "Lỗi khi tải danh sách bài hát")
            _state.update { it.copy(isLoading = false, error = "Lỗi khi tải danh sách bài hát: $e") }
        }
    }



    class LibraryViewModelFactory(private val context: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LibraryViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LibraryViewModel(context) as T
            }
            throw IllegalArgumentException("Unable to construct LibraryViewModelFactory")
        }
    }
}