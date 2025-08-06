package com.example.dinhngocthe.presentation.library

import android.os.Message
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.dinhngocthe.R
import com.example.dinhngocthe.data.local.entities.Song
import com.example.dinhngocthe.data.local.entities.SongSource
import com.example.dinhngocthe.presentation.components.LibraryDropDownMenu
import com.example.dinhngocthe.utils.formatDuration

@Composable
fun MainLibrary(
    songSource: SongSource,
    localSongs: List<Song>,
    remoteSongs: List<Song>,
    expandedIndex: Int,
    isLoadingRemoteSongs: Boolean,
    remoteError: String,
    modifier: Modifier = Modifier,
    onDismissMenu: () -> Unit,
    onShowMenu: (Int) -> Unit,
    onInsertToPlaylist: (Long) -> Unit,
    reload: () -> Unit
) {
    val songs = if (songSource == SongSource.LOCAL) localSongs else remoteSongs

    if (songSource == SongSource.LOCAL) { // Local mode
        LazyColumn(
            modifier = modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(songs.size) { index ->
                SongItem(
                    song = songs[index],
                    index = index,
                    expandedIndex = expandedIndex,
                    onDismissMenu = onDismissMenu,
                    onShowMenu = { onShowMenu(index) },
                    addToPlaylist = { onInsertToPlaylist(songs[index].songId) }
                )
            }
        }
    } else { // Remote mode
        // animation loading remote songs
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = LottieConstants.IterateForever
        )
        if (isLoadingRemoteSongs) {
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
            if (remoteError == "") {
                LazyColumn(
                    modifier = modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(songs.size) { index ->
                        SongItem(
                            song = songs[index],
                            index = index,
                            expandedIndex = expandedIndex,
                            onDismissMenu = onDismissMenu,
                            onShowMenu = { onShowMenu(index) },
                            addToPlaylist = { onInsertToPlaylist(songs[index].songId) }
                        )
                    }
                }
            } else {
                LoadMusicError(
                    message = remoteError,
                    reload = { reload() }
                )
            }
        }
    }
}

@Composable
private fun SongItem(
    song: Song,
    index: Int,
    expandedIndex: Int,
    modifier: Modifier = Modifier,
    onDismissMenu: () -> Unit,
    onShowMenu: (Int) -> Unit,
    addToPlaylist: (Int) -> Unit
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Row {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(song.coverArtUri)
                    .size(200)
                    .build(),
                contentDescription = "Cover art",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(5.dp)),
                error = painterResource(R.drawable.img_song_default)
            )
            Spacer(Modifier.size(15.dp))
            Column(modifier = Modifier
                .fillMaxHeight()
                .padding(top = 2.dp)) {
                Text(
                    song.songName,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelSmall
                )
                Text(
                    song.singer,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 12.sp)
                )
            }
        }

        Row(
            modifier = Modifier.align(Alignment.CenterEnd),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = formatDuration(song.duration),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(end = 10.dp)
            )

            Box {
                IconButton(
                    onClick = { onShowMenu(index) },
                    modifier = Modifier.size(35.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_menu),
                        contentDescription = "Menu",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }

                if (expandedIndex == index) {
                    LibraryDropDownMenu(
                        expanded = true,
                        onDismissRequest = onDismissMenu,
                        addToPlaylist = { addToPlaylist(index) },
                        onShare = { /* TODO */ }
                    )
                }
            }
        }
    }
}

@Composable
fun LoadMusicError(
    modifier: Modifier = Modifier,
    message: String,
    reload: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_load_music_error),
            contentDescription = "image load music error",
            modifier = Modifier.size(120.dp),
            tint = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = message,
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 20.sp
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally).width(300.dp),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.size(30.dp))
        Button(
            onClick = { reload() },
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.onPrimary,
                containerColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.size(120.dp, 45.dp).clip(RoundedCornerShape(10.dp))
        ) {
            Text(
                text = "Try again",
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Preview
@Composable
private fun LoadMusicErrorPrev() {
    LoadMusicError(reload = {}, message = "No internet connection, please check your connection again")
}