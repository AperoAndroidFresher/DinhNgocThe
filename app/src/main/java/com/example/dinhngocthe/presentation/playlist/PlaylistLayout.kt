package com.example.dinhngocthe.presentation.playlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.dinhngocthe.R
import com.example.dinhngocthe.data.room.entities.Playlist
import com.example.dinhngocthe.presentation.theme.AppFonts
import com.example.dinhngocthe.presentation.view.PlaylistDialog
import com.example.dinhngocthe.presentation.view.PlaylistDropDownMenu

@Composable
fun PlaylistLayout(
    isInsertPlaylistDialogVisible: Boolean,
    playlists: List<Playlist>,
    playlistMenuIndex: Int,
    isRenamePlaylistDialogVisible: Boolean,
    headerPlaylistModifier: Modifier = Modifier,
    mainPlaylistModifier: Modifier = Modifier,
    onChangeInsertPlaylistDialogState: (Boolean) -> Unit,
    onSelectPlaylistIndex: (Int) -> Unit,
    onSelectPlaylistMenuIndex: (Int) -> Unit,
    onDeletePlaylist: (Playlist) -> Unit,
    onChangeRenamePlaylistDialogState: (Boolean) -> Unit,
    onChangeRenamingPlaylistIndex: (Int) -> Unit,
    onInsertPlaylist: (String) -> Unit,
    onRenamePlaylist: (String) -> Unit
) {
    HeaderPlaylistLayout(
        addPlaylist = { onChangeInsertPlaylistDialogState(true) },
        modifier = headerPlaylistModifier
    )

    MainPlaylist(
        playlists = playlists,
        playlistMenuIndex = playlistMenuIndex,
        modifier = mainPlaylistModifier,
        onSelectPlaylist = { onSelectPlaylistIndex(it) },
        onSelectPlaylistMenuIndex = { onSelectPlaylistMenuIndex(it) },
        onDismissPlaylistMenu = { onSelectPlaylistMenuIndex(-1) },
        onDeletePlaylist = { onDeletePlaylist(it) },
        showRenameDialog = {
            onChangeRenamePlaylistDialogState(true)
            onChangeRenamingPlaylistIndex(it)
        }
    )

    if (isInsertPlaylistDialogVisible == true) {
        PlaylistDialog(
            onDismiss = { onChangeInsertPlaylistDialogState(false) },
            playlistAction =  { onInsertPlaylist(it) }
        )
    }

    if (isRenamePlaylistDialogVisible == true) {
        PlaylistDialog(
            onDismiss = { onChangeRenamePlaylistDialogState(false) },
            playlistAction =  { onRenamePlaylist(it) },
            title = "Rename Playlist",
            actionName = "Update"
        )
    }

    if (playlists.isEmpty()) {
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
                    onClick = { onChangeInsertPlaylistDialogState(true) },
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
}

@Composable
fun HeaderPlaylistLayout(
    addPlaylist: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp)
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
    playlistMenuIndex: Int,
    modifier: Modifier = Modifier,
    onSelectPlaylist: (Int) -> Unit,
    onSelectPlaylistMenuIndex: (Int) -> Unit,
    onDismissPlaylistMenu: () -> Unit,
    onDeletePlaylist: (Playlist) -> Unit,
    showRenameDialog: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth()
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
                        onClick = { onSelectPlaylistMenuIndex(index) },
                        modifier = Modifier.size(35.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_menu),
                            contentDescription = "Menu",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    if (playlistMenuIndex == index) {
                        PlaylistDropDownMenu(
                            expanded = true,
                            onDismissRequest = onDismissPlaylistMenu,
                            onRemove = { onDeletePlaylist(playlists[index]) },
                            onRename = { showRenameDialog(index) }
                        )
                    }
                }
            }
        }
    }
}