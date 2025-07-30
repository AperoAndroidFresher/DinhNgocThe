package com.example.dinhngocthe.presentation.playlist

import androidx.lifecycle.ViewModel
import com.example.dinhngocthe.R
import com.example.dinhngocthe.model.Song
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PlaylistViewModel : ViewModel() {
    private val _state = MutableStateFlow(PlaylistState(songs = fakeSongs()))
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
                // Nếu cần share thật thì xử lý ở đây (ví dụ gửi Event)
                _state.update { it.copy(expandedMenuIndex = -1) }
            }
        }
    }

    private fun fakeSongs(): List<Song> = listOf(
        Song("1000 Ánh Mắt", "Shiki, Obito", 200_000L, R.drawable.img_1000_anh_mat),
        Song("Giấc Mơ Khác", "Chillies", 210_000L, R.drawable.img_giac_mo_khac),
        Song("Lặng", "Rhymastic", 200_000L, R.drawable.img_lang),
        Song("Em Không Hiểu", "Changg, Minh Huy", 235_000L, R.drawable.img_em_khong_hieu),
        Song("1000 Ánh Mắt", "Shiki, Obito", 200_000L, R.drawable.img_1000_anh_mat),
        Song("Giấc Mơ Khác", "Chillies", 210_000L, R.drawable.img_giac_mo_khac),
        Song("Lặng", "Rhymastic", 200_000L, R.drawable.img_lang),
        Song("Em Không Hiểu", "Changg, Minh Huy", 235_000L, R.drawable.img_em_khong_hieu),
        Song("1000 Ánh Mắt", "Shiki, Obito", 200_000L, R.drawable.img_1000_anh_mat),
        Song("Giấc Mơ Khác", "Chillies", 210_000L, R.drawable.img_giac_mo_khac),
        Song("Lặng", "Rhymastic", 200_000L, R.drawable.img_lang),
        Song("Em Không Hiểu", "Changg, Minh Huy", 235_000L, R.drawable.img_em_khong_hieu),
        Song("1000 Ánh Mắt", "Shiki, Obito", 200_000L, R.drawable.img_1000_anh_mat),
        Song("Giấc Mơ Khác", "Chillies", 210_000L, R.drawable.img_giac_mo_khac),
        Song("Lặng", "Rhymastic", 200_000L, R.drawable.img_lang),
        Song("Em Không Hiểu", "Changg, Minh Huy", 235_000L, R.drawable.img_em_khong_hieu),
        Song("1000 Ánh Mắt", "Shiki, Obito", 200_000L, R.drawable.img_1000_anh_mat),
        Song("Giấc Mơ Khác", "Chillies", 210_000L, R.drawable.img_giac_mo_khac),
        Song("Lặng", "Rhymastic", 200_000L, R.drawable.img_lang),
        Song("Em Không Hiểu", "Changg, Minh Huy", 235_000L, R.drawable.img_em_khong_hieu)
    )
}
