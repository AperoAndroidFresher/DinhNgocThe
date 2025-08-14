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

    fun handleLoadData() {
        loadUser()
        loadTopAlbums()
        loadTopTracks()
        loadTopArtists()
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
            userRepository.getUserByUserId(userId!!).collectLatest { user ->
                _state.update { it.copy(user = user) }
                setLoading(false)
            }
        }
    }

    private fun loadTopAlbums() {
        setLoading(true)
        homeRepository.getTopAlbums(
            onSuccess = { topAlbumResponse ->
                _state.update { it.copy(topAlbums = topAlbumResponse, error = "") }
                setLoading(false)
            },
            onFailure = { error ->
                _state.update { it.copy(error = error.message.toString()) }
                setLoading(false)
            }
        )
    }

    private fun loadTopTracks() {
        setLoading(true)
        homeRepository.getTopTracks(
            onSuccess = { topTracks ->
                _state.update { it.copy(topTracks = topTracks, error = "") }
                setLoading(false)
            },
            onFailure = { error ->
                _state.update { it.copy(error = error.message.toString()) }
                setLoading(false)
            }
        )
    }

    private fun loadTopArtists() {
        setLoading(true)
        homeRepository.getTopArtists(
            onSuccess = { topArtists ->
                _state.update { it.copy(topArtists = topArtists, error = "") }
                setLoading(false)
                Log.d("HomeViewModelTopArtists", topArtists.artists.artist.size.toString())
            },
            onFailure = { error ->
                _state.update { it.copy(error = error.message.toString()) }
                setLoading(false)
            }
        )
    }
}
