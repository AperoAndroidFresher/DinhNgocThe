package com.example.dinhngocthe.presentation.playlist

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.dinhngocthe.R
import com.example.dinhngocthe.data.room.entities.Playlist
import com.example.dinhngocthe.data.room.entities.Song
import com.example.dinhngocthe.presentation.login.CurrentUser
import com.example.dinhngocthe.presentation.theme.AppFonts
import com.example.dinhngocthe.presentation.view.PlaylistDialog
import com.example.dinhngocthe.presentation.view.PlaylistDropDownMenu
import com.example.dinhngocthe.presentation.view.SongDropDownMenu
import com.example.dinhngocthe.utils.formatDuration
import com.example.dinhngocthe.utils.transformSongWithPlaylistIdToSong

@Composable
fun MyPlaylistScreen(
    innerPadding: PaddingValues
) {
    val context = LocalContext.current.applicationContext as Application
    val viewModel: PlaylistViewModel = viewModel(
        factory = remember { PlaylistViewModel.PlaylistViewModelFactory(context) }
    )
    val state by viewModel.state.collectAsStateWithLifecycle()

    //Playlist state
    var selectedPlaylist by remember { mutableStateOf(-1) }
    var showAddPlaylistDialog by remember { mutableStateOf(false) }
    var expandedPlaylistDropDownMenuIndex by remember { mutableStateOf(-1) }
    var renameIndex by remember { mutableStateOf(-1) }
    var showRenamePlaylistDialog by remember { mutableStateOf(false) }

    //ListSongs state
    var isListMode by remember { mutableStateOf(true) }
    var iconDisplayMode = if (isListMode) R.drawable.ic_list_mode else R.drawable.ic_grid_mode
    var expandedListSongsDropDownMenuIndex by remember { mutableStateOf(-1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(
                top = innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding()
            )
    ) {
        if (selectedPlaylist == -1 ) { //When playlist mode
            HeaderPlaylist(
                addPlaylist = {
                    showAddPlaylistDialog = true
                }
            )

            MainPlaylist(
                playlists = state.playlists,
                onSelectPlaylist = { selectedPlaylist = it },
                expandedPlaylistDropDownMenuIndex = expandedPlaylistDropDownMenuIndex,
                showPlaylistDropDownMenu = { expandedPlaylistDropDownMenuIndex = it },
                onDismissPlaylistDropDownMenu = { expandedPlaylistDropDownMenuIndex = -1 },
                onRemovePlaylist = { viewModel.processIntent(PlaylistIntent.RemovePlaylist(it)) },
                showRenameDialog = {
                    showRenamePlaylistDialog = true
                    renameIndex = it
                }
            )

            if (showAddPlaylistDialog == true) PlaylistDialog(
                onDismiss = { showAddPlaylistDialog = false },
                playlistAction =  { viewModel.processIntent(PlaylistIntent.AddPlaylist(Playlist(
                    playlistName = it,
                    userId = CurrentUser.id
                ))) }
            )

            if (showRenamePlaylistDialog == true) PlaylistDialog(
                onDismiss = { showRenamePlaylistDialog = false },
                playlistAction =  { viewModel.processIntent(PlaylistIntent.RenamePlaylist(id = state.playlists[renameIndex].playlistId, name = it)) },
                title = "Rename Playlist",
                actionName = "Update"
            )

            if (state.playlists.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize().padding(start = 70.dp, end = 70.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "You don't have any playlists. Click the “+” button to add",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontFamily = AppFonts.mainFont,
                                color = MaterialTheme.colorScheme.onSurface
                            ),
                            textAlign = TextAlign.Center
                        )
                        IconButton(
                            onClick = { showAddPlaylistDialog = true },
                            modifier = Modifier.size(90.dp)
                        ) {
                            Icon(
                                contentDescription = "Press to add playlist",
                                modifier = Modifier.size(70.dp),
                                painter = painterResource(R.drawable.ic_add_playlist)
                            )
                        }
                    }
                }
            }
        } else { //When choose playlist
            HeaderListSongs(
                title = state.playlists[selectedPlaylist].playlistName,
                onChangeDisplayMode = { isListMode = !isListMode },
                iconDisplayMode = iconDisplayMode,
                goBackPlaylistMode = { selectedPlaylist = -1 }
            )

            if (isListMode) {
                ListSongs(
                    songs = transformSongWithPlaylistIdToSong(state.playlists[selectedPlaylist].playlistId, state.songs),
                    expandedIndex = expandedListSongsDropDownMenuIndex,
                    onShowMenu = { expandedListSongsDropDownMenuIndex = it },
                    onDismissMenu = { expandedListSongsDropDownMenuIndex = -1 },
                    onRemoveSong = { viewModel.processIntent(PlaylistIntent.RemoveSong(playlistId = state.playlists[selectedPlaylist].playlistId, songId = it)) }
                )
            } else {
                GridSongs(
                    songs = transformSongWithPlaylistIdToSong(state.playlists[selectedPlaylist].playlistId, state.songs),
                    expandedIndex =  expandedListSongsDropDownMenuIndex,
                    onShowMenu = { expandedListSongsDropDownMenuIndex = selectedPlaylist },
                    onDismissMenu = { expandedListSongsDropDownMenuIndex = -1 },
                    onRemove = { viewModel.processIntent(PlaylistIntent.RemoveSong(playlistId = state.playlists[selectedPlaylist].playlistId, songId = it)) }
                )
            }
        }
    }
}

@Composable
fun HeaderPlaylist(
    addPlaylist: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp)
    ) {
        Text(
            "My Playlist",
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.Center)
        )

        IconButton(
            onClick = addPlaylist,
            modifier = Modifier.size(40.dp).align(Alignment.CenterEnd)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_plus),
                contentDescription = "Switch display mode",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(20.dp)
            )
        }
    }

    Spacer(Modifier.size(20.dp))
}

@Composable
fun MainPlaylist(
    playlists: List<Playlist>,
    onSelectPlaylist: (Int) -> Unit,
    expandedPlaylistDropDownMenuIndex: Int,
    showPlaylistDropDownMenu: (Int) -> Unit,
    onDismissPlaylistDropDownMenu: () -> Unit,
    onRemovePlaylist: (Playlist) -> Unit,
    showRenameDialog: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(playlists.size) { index ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .clickable{ onSelectPlaylist(index) }
                    .padding(horizontal = 20.dp, vertical = 5.dp)

            ) {
                Row {
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(playlists[index].coverArtUri)
                            .size(200)
                            .build(),
                        contentDescription = "Cover art",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(RoundedCornerShape(5.dp)),
                        error = painterResource(R.drawable.img_cover_art_default)
                    )
                    Spacer(Modifier.size(15.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(top = 2.dp)
                    ) {
                        Text(
                            playlists[index].playlistName,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            playlists[index].numberOfSongs.toString() + " songs",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 12.sp)
                        )
                    }
                }

                Box(
                    modifier = Modifier.size(35.dp).align(Alignment.CenterEnd)
                ) {
                    IconButton(
                        onClick = { showPlaylistDropDownMenu(index) },
                        modifier = Modifier.size(35.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_menu),
                            contentDescription = "Menu",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    if (expandedPlaylistDropDownMenuIndex == index) {
                        PlaylistDropDownMenu(
                            expanded = true,
                            onDismissRequest = onDismissPlaylistDropDownMenu,
                            onRemove = { onRemovePlaylist(playlists[index]) },
                            onRename = { showRenameDialog(index) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HeaderListSongs(
    title: String,
    onChangeDisplayMode: () -> Unit,
    iconDisplayMode: Int,
    goBackPlaylistMode: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(
            onClick = goBackPlaylistMode,
            modifier = Modifier.size(40.dp).align(Alignment.CenterStart)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = "Go back",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(20.dp)
            )
        }

        Text(
            text = title,
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
                onClick = onChangeDisplayMode,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    painter = painterResource(iconDisplayMode),
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

    Spacer(Modifier.size(20.dp))
}

@Composable
fun ListSongs(
    songs: List<Song>,
    expandedIndex: Int,
    onShowMenu: (Int) -> Unit,
    onDismissMenu: () -> Unit,
    onRemoveSong: (Long) -> Unit
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
                            .data(songs[index].coverArtUri)
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
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(top = 2.dp)
                    ) {
                        Text(
                            songs[index].songName,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            songs[index].singer,
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
                            SongDropDownMenu(
                                expanded = true,
                                onDismissRequest = onDismissMenu,
                                onRemove = { onRemoveSong(songs[index].songId) },
                                onShare = { }
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
    onRemove: (Long) -> Unit
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
                            .data(songs[index].coverArtUri)
                            .size(450)
                            .build(),
                        contentDescription = "Cover art",
                        modifier = Modifier
                            .clip(RoundedCornerShape(7.dp))
                            .size(150.dp),
                        error = painterResource(R.drawable.img_song_default)
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
                        SongDropDownMenu(
                            expanded = true,
                            onDismissRequest = onDismissMenu,
                            onRemove = { onRemove(songs[index].songId) },
                            onShare = {  }
                        )
                    }
                }
                Spacer(Modifier.size(15.dp))
                Text(
                    text = songs[index].songName,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 18.sp),
                    modifier = Modifier.padding(horizontal = 30.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = songs[index].singer,
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


