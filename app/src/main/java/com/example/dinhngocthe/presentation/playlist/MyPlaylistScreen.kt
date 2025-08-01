package com.example.dinhngocthe.presentation.playlist

import android.annotation.SuppressLint
import android.app.Application
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.dinhngocthe.R
import com.example.dinhngocthe.model.Song
import com.example.dinhngocthe.presentation.view.MenuSongDropDown

@Composable
fun MyPlaylistScreen(
    innerPadding: PaddingValues
) {
    val context = LocalContext.current.applicationContext as Application
    val viewModel: PlaylistViewModel = viewModel(
        factory = remember { PlaylistViewModel.PlaylistViewModelFactory(context) }
    )
    val state by viewModel.state.collectAsStateWithLifecycle()
    val icon = if (state.isListMode) R.drawable.ic_grid_mode else R.drawable.ic_list_mode

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(
                top = innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding()
            )
    ) {
        Header(
            onClick = { viewModel.processIntent(PlaylistIntent.ToggleDisplayMode) },
            iconButtonChangeMode = icon
        )

        Spacer(Modifier.size(30.dp))

        if (state.isListMode) {
            ListSongs(
                songs = state.songs,
                expandedIndex = state.expandedMenuIndex,
                onShowMenu = { viewModel.processIntent(PlaylistIntent.ShowMenu(it)) },
                onDismissMenu = { viewModel.processIntent(PlaylistIntent.DismissMenu) },
                onRemoveSong = { viewModel.processIntent(PlaylistIntent.RemoveSong(it)) },
                onShare = { viewModel.processIntent(PlaylistIntent.ShareSong(it)) }
            )
        } else {
            GridSongs(
                songs = state.songs,
                expandedIndex = state.expandedMenuIndex,
                onShowMenu = { viewModel.processIntent(PlaylistIntent.ShowMenu(it)) },
                onDismissMenu = { viewModel.processIntent(PlaylistIntent.DismissMenu) },
                onRemove = { viewModel.processIntent(PlaylistIntent.RemoveSong(it)) },
                onShare = { viewModel.processIntent(PlaylistIntent.ShareSong(it)) }
            )
        }
    }
}

@Composable
fun Header(onClick: () -> Unit, iconButtonChangeMode: Int) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            "My Playlist",
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.Center)
        )

        Row(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 10.dp)
        ) {
            IconButton(
                onClick = onClick,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    painter = painterResource(iconButtonChangeMode),
                    contentDescription = "Switch display mode",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(20.dp)
                )
            }

            IconButton(
                onClick = { },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_sort),
                    contentDescription = "Sort",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(25.dp)
                )
            }
        }
    }
}

@Composable
fun ListSongs(
    songs: List<Song>,
    expandedIndex: Int,
    onShowMenu: (Int) -> Unit,
    onDismissMenu: () -> Unit,
    onRemoveSong: (Int) -> Unit,
    onShare: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(songs.size) { index ->
            Box(modifier = Modifier.fillMaxWidth()) {
                Row {
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(songs[index].coverArt)
                            .size(200)
                            .build(),
                        contentDescription = "Cover art",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(RoundedCornerShape(5.dp))
                    )
                    Spacer(Modifier.size(15.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(top = 2.dp)
                    ) {
                        Text(
                            songs[index].name,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            songs[index].singers,
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
                        text = formatDuration(songs[index].duration),
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
                            MenuSongDropDown(
                                expanded = true,
                                onDismissRequest = onDismissMenu,
                                onRemove = { onRemoveSong(index) },
                                onShare = { onShare(index) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GridSongs(
    songs: List<Song>,
    expandedIndex: Int,
    onShowMenu: (Int) -> Unit,
    onDismissMenu: () -> Unit,
    onRemove: (Int) -> Unit,
    onShare: (Int) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    LazyVerticalGrid(
        columns = GridCells.Fixed((screenWidth.value / 200.dp.value).toInt()),
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(songs.size) { index ->
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier.padding(horizontal = 30.dp)) {
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(songs[index].coverArt)
                            .size(450)
                            .build(),
                        contentDescription = "Cover art",
                        modifier = Modifier
                            .clip(RoundedCornerShape(7.dp))
                            .size(150.dp)
                    )
                    Button(
                        onClick = { onShowMenu(index) },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .size(34.dp)
                            .clip(CircleShape),
                        contentPadding = PaddingValues(7.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f)
                        )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_menu),
                            contentDescription = "Menu",
                            modifier = Modifier.fillMaxSize(),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    if (expandedIndex == index) {
                        MenuSongDropDown(
                            expanded = true,
                            onDismissRequest = onDismissMenu,
                            onRemove = { onRemove(index) },
                            onShare = { onShare(index) }
                        )
                    }
                }
                Spacer(Modifier.size(15.dp))
                Text(
                    text = songs[index].name,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 18.sp),
                    modifier = Modifier.padding(horizontal = 30.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = songs[index].singers,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(horizontal = 30.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = formatDuration(songs[index].duration),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

@SuppressLint("DefaultLocale")
private fun formatDuration(durationInMillis: Long): String {
    val totalSeconds = durationInMillis / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format("%d:%02d", minutes, seconds)
}
