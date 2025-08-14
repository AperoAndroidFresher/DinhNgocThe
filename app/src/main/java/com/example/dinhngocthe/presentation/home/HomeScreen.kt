package com.example.dinhngocthe.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.dinhngocthe.R
import com.example.dinhngocthe.presentation.library.LoadMusicError
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    innerPadding: PaddingValues,
    navigateToProfileScreen: () -> Unit,
    navigateToSettingScreen: () -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.processIntent(HomeIntent.LoadData)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(
                top = innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding()
            )
    ) {
        HeaderHome(
            user = state.user,
            modifier = Modifier,
            navigateToProfileScreen = { navigateToProfileScreen() },
            navigateToSettingScreen = { navigateToSettingScreen() }
        )

        if (state.isLoading) {
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
            val progress by animateLottieCompositionAsState(
                composition = composition,
                iterations = LottieConstants.IterateForever
            )
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                    modifier = Modifier.size(120.dp)
                )
            }
        } else {
            if (state.error == "") {
                MainHome(
                    modifier = Modifier,
                    topAlbums = state.topAlbums,
                    topTracks = state.topTracks?.toptracks,
                    topArtists = state.topArtists
                )
            } else {
                LoadMusicError(
                    message = state.error,
                    enableButtonViewOffline = false,
                    reload = { viewModel.handleLoadData() }
                )
            }
        }
    }
}

