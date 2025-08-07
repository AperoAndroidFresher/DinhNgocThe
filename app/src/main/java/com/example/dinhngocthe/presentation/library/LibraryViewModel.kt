package com.example.dinhngocthe.presentation.library

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.dinhngocthe.data.local.datasource.DeviceSongDataSource
import com.example.dinhngocthe.data.local.datasource.DeviceSongDataSourceImpl
import com.example.dinhngocthe.data.local.datasource.DownloadSongDataSource
import com.example.dinhngocthe.data.local.datasource.DownloadSongDataSourceImpl
import com.example.dinhngocthe.data.local.entities.Playlist
import com.example.dinhngocthe.data.local.entities.PlaylistSongCrossRef
import com.example.dinhngocthe.data.local.entities.Song
import com.example.dinhngocthe.data.local.entities.SongSource
import com.example.dinhngocthe.data.repository.PlaylistRepositoryImpl
import com.example.dinhngocthe.data.repository.SongRepositoryImpl
import com.example.dinhngocthe.domain.repository.PlaylistRepository
import com.example.dinhngocthe.domain.repository.SongRepository
import com.example.dinhngocthe.presentation.login.CurrentUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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

    private val deviceSongDataSource: DeviceSongDataSource = DeviceSongDataSourceImpl()
    private val downloadSongDataSource: DownloadSongDataSource = DownloadSongDataSourceImpl()
    private val songRepository: SongRepository = SongRepositoryImpl(context, deviceSongDataSource, downloadSongDataSource)
    private val playlistRepository: PlaylistRepository = PlaylistRepositoryImpl(context)

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

            LibraryIntent.ViewOffline -> {
                handleViewOffline()
            }
        }
    }

    private fun handleLoadData() {
        loadSongsFromRemoteAndSaveToRoom()

        viewModelScope.launch(Dispatchers.IO) {
            loadLocalSongsAndSaveToRoom()
        }

        viewModelScope.launch {
            playlistRepository.getAllPlaylistsByUserId(CurrentUser.id).collectLatest { playlists: List<Playlist> ->
                _state.update { it.copy(playlists = playlists) }
            }
        }

        viewModelScope.launch {
            songRepository.getSongsFromRoom().collectLatest { songs: List<Song> ->
                _state.update {
                    it.copy(
                        localSongs = songs.filter { it.source == SongSource.LOCAL },
                        remoteSongs = songs.filter { it.source == SongSource.REMOTE }
                    )
                }
            }
        }
    }

    suspend fun loadLocalSongsAndSaveToRoom() {
        _state.update { it.copy(isLoadingLocalSongs = true, localError = "") }
        val songs: List<Song>? = songRepository.loadLocalSongsFromDevice()

        if (songs == null) {
            _state.update { it.copy(isLoadingLocalSongs = false, localError = "Error when loading songs") }
        } else {
            songRepository.insertAllSongsToRoom(songs)
            _state.update { it.copy(isLoadingLocalSongs = false) }
        }
    }

    fun loadSongsFromRemoteAndSaveToRoom() {
        _state.update { it.copy(isLoadingRemoteSongs = true, remoteError = "") }
        songRepository.getSongsFromRemote(
            onSuccess = { songDtos ->
                viewModelScope.launch(Dispatchers.IO) {
                    val songs = songRepository.downloadAndSaveSongDtosToStorage(songDtos)
                    songRepository.insertAllSongsToRoom(songs)
                    delay(1000)
                    _state.update { it.copy(isLoadingRemoteSongs = false) }
                }
            },
            onFailure = { throwable ->
                _state.update { it.copy(isLoadingRemoteSongs = false, remoteError = throwable.message.toString()) }
            }
        )
    }

    private fun handleAddMusicToPlaylist(intent: LibraryIntent.AddMusicToPlaylist) {
        viewModelScope.launch(context = Dispatchers.IO) {
            val playlistSongCrossRef = PlaylistSongCrossRef(
                playlistId = intent.playlistId,
                songId = intent.songId
            )
            songRepository.insertSongToPlaylistInRoom(playlistSongCrossRef = playlistSongCrossRef)
            playlistRepository.updatePlaylistSongCount(playlistId = intent.playlistId)
        }
    }

    private fun handleNavigateToPlaylist() {
        viewModelScope.launch {
            _event.emit(LibraryEvent.NavigateToPlaylist)
        }
    }

    private fun handleViewOffline() {
        _state.update { it.copy(remoteError = "") }
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