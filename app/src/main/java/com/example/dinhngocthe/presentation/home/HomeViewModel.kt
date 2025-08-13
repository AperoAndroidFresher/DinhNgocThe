package com.example.dinhngocthe.presentation.home

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dinhngocthe.data.local.datastore.UserDataStore
import com.example.dinhngocthe.domain.repository.HomeRepository
import com.example.dinhngocthe.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userDataStore: UserDataStore,
    private val userRepository: UserRepository,
    private val homeRepository: HomeRepository
) : ViewModel() {
    private val _state: MutableStateFlow<HomeState> = MutableStateFlow<HomeState>(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    fun processIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.LoadData -> {
                handleLoadData()
            }
        }
    }

    private fun handleLoadData() {
        loadUser()
        loadTopAlbums()
    }

    private fun loadUser() {
        viewModelScope.launch() {
            val userId = userDataStore.getUserId()
            userRepository.getUserByUserId(userId!!).collectLatest {
                _state.value = _state.value.copy(user = it)
            }
        }
    }

    private fun loadTopAlbums() {
        _state.update { it.copy(isLoading = true, error = "") }
        homeRepository.getTopAlbums(
            onSuccess = {   topAlbumResponse ->
                _state.update { it.copy(isLoading = false, topAlbums = topAlbumResponse) }
            },
            onFailure = {
                _state.update { it.copy(isLoading = false, error = it.error) }
            }
        )
    }
}

//_state.update { it.copy(isLoadingRemoteSongs = true, remoteError = "") }
//songRepository.getSongsFromRemote(
//onSuccess = { songDtos ->
//    viewModelScope.launch(Dispatchers.IO) {
//        val songs = songRepository.downloadAndSaveSongDtosToStorage(songDtos)
//        songRepository.insertAllSongsToRoom(songs)
//        delay(1000)
//        _state.update { it.copy(isLoadingRemoteSongs = false) }
//    }
//},
//onFailure = { throwable ->
//    _state.update { it.copy(isLoadingRemoteSongs = false, remoteError = throwable.message.toString()) }
//}
//)