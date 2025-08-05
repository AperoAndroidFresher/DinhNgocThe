package com.example.dinhngocthe.presentation.library

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.dinhngocthe.data.repository.PlaylistRepository
import com.example.dinhngocthe.data.repository.SongRepository
import com.example.dinhngocthe.data.room.entities.Playlist
import com.example.dinhngocthe.data.room.entities.PlaylistSongCrossRef
import com.example.dinhngocthe.data.room.entities.Song
import com.example.dinhngocthe.presentation.login.CurrentUser
import com.example.dinhngocthe.presentation.permission.RequestAudioPermissionIfNeeded
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LibraryViewModel(context: Application) : ViewModel() {
    val tag = "LibraryViewModel"

    private val songRepository = SongRepository(context)
    private val playlistRepository = PlaylistRepository(context)

    private val _state: MutableStateFlow<LibraryState> = MutableStateFlow<LibraryState>(LibraryState())
    val state: StateFlow<LibraryState> = _state.asStateFlow()

    private val _event: MutableSharedFlow<LibraryEvent> = MutableSharedFlow<LibraryEvent>()
    val event: SharedFlow<LibraryEvent> = _event.asSharedFlow()

    fun processIntent(intent: LibraryIntent) {
        when(intent) {
            LibraryIntent.LoadData -> {
                handleLoadData()
            }

            is LibraryIntent.AddMusicToPlaylist -> {
                handleAddMusicToPlaylist(intent)
            }

            LibraryIntent.NavigateToPlaylist -> {
                handleNavigateToPlaylist()
            }
        }
    }

    private fun handleLoadData() {
        viewModelScope.launch(Dispatchers.IO) {
            loadLocalSongsAndSaveToRoom()
        }

        viewModelScope.launch {
            playlistRepository.getAllPlaylistsByUserId(CurrentUser.id).collectLatest { playlists: List<Playlist> ->
                _state.update { it.copy(playlists = playlists) }
            }
        }

        viewModelScope.launch {
            songRepository.getAllSongs().collectLatest { songs: List<Song> ->
                _state.update { it.copy(localSongs = songs) }
            }
        }
    }

    suspend fun loadLocalSongsAndSaveToRoom() {
        _state.update { it.copy(isLoadingLocalSongs = true) }
        val songs: List<Song>? = songRepository.loadLocalSongs()

        if (songs == null) {
            _state.update { it.copy(isLoadingLocalSongs = false, error = "Error when loading songs") }
        } else {
            songRepository.insertAllSongs(songs)
            _state.update { it.copy(isLoadingLocalSongs = false) }
        }
    }

    private fun handleAddMusicToPlaylist(intent: LibraryIntent.AddMusicToPlaylist) {
        viewModelScope.launch(context = Dispatchers.IO) {
            val playlistSongCrossRef = PlaylistSongCrossRef(
                playlistId = intent.playlistId,
                songId = intent.songId
            )
            songRepository.addSongToPlaylist(playlistSongCrossRef = playlistSongCrossRef)
            playlistRepository.updatePlaylistSongCount(playlistId = intent.playlistId)
        }
    }

    private fun handleNavigateToPlaylist() {
        viewModelScope.launch {
            _event.emit(LibraryEvent.NavigateToPlaylist)
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