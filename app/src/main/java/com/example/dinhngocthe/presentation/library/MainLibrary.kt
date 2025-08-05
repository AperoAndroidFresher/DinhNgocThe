package com.example.dinhngocthe.presentation.library

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.dinhngocthe.R
import com.example.dinhngocthe.data.room.entities.Song
import com.example.dinhngocthe.presentation.view.LibraryDropDownMenu
import com.example.dinhngocthe.utils.formatDuration

@Composable
fun MainLibrary(
    displayMode: String,
    localSongs: List<Song>,
    remoteSongs: List<Song>,
    expandedIndex: Int,
    modifier: Modifier = Modifier,
    onDismissMenu: () -> Unit,
    onShowMenu: (Int) -> Unit,
    onInsertToPlaylist: (Int) -> Unit
) {
    val songs = if (displayMode == "local") localSongs else remoteSongs

    if (songs.isNotEmpty()) {
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
                    onShowMenu = onShowMenu,
                    addToPlaylist = onInsertToPlaylist
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
                    .clip(RoundedCornerShape(5.dp))
            )
            Spacer(Modifier.size(15.dp))
            Column(modifier = Modifier.fillMaxHeight().padding(top = 2.dp)) {
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
