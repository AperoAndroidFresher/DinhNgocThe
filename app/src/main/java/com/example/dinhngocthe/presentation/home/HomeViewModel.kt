package com.example.dinhngocthe.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dinhngocthe.data.local.datastore.UserDataStore
import com.example.dinhngocthe.domain.repository.HomeRepository
import com.example.dinhngocthe.domain.repository.UserRepository
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
    private var loadingCount = 0

    fun processIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.LoadData -> {
                handleLoadData()
            }
        }
    }

    private fun handleLoadData() {
        loadUser()
        loadTopAlbums()
        loadTopTracks()
    }

    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            loadingCount++
        } else {
            loadingCount--
        }
        _state.update { it.copy(isLoading = loadingCount > 0) }
    }

    private fun loadUser() {
        viewModelScope.launch {
            setLoading(true)
            val userId = userDataStore.getUserId()
            userRepository.getUserByUserId(userId!!).collectLatest {
                _state.update { it.copy(user = it.user) }
                setLoading(false)
            }
        }
    }

    private fun loadTopAlbums() {
        setLoading(true)
        homeRepository.getTopAlbums(
            onSuccess = { topAlbumResponse ->
                _state.update { it.copy(topAlbums = topAlbumResponse) }
                setLoading(false)
            },
            onFailure = {
                _state.update { it.copy(error = it.error) }
                setLoading(false)
            }
        )
    }

    private fun loadTopTracks() {
        Log.d("HomeViewModel", "Load top tracks")
        setLoading(true)
        homeRepository.getTopTracks(
            onSuccess = { topTracks ->
                _state.update { it.copy(topTracks = topTracks) }
                setLoading(false)
                Log.d("HomeViewModel Success", topTracks.topTracks.track.size.toString())
            },
            onFailure = {
                _state.update { it.copy(error = it.error) }
                setLoading(false)
                Log.d("HomeViewModel Failure", it.message.toString())
            }
        )
    }
}
