package com.example.dinhngocthe.presentation.playlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.dinhngocthe.R
import com.example.dinhngocthe.data.room.entities.Song
import com.example.dinhngocthe.presentation.view.SongDropDownMenu
import com.example.dinhngocthe.utils.formatDuration

@Composable
fun SongLayout(
    playlistName: String,
    iconDisplayMode: Int,
    isListLayout: Boolean,
    songs: List<Song>,
    songMenuIndex: Int,
    headerModifier: Modifier = Modifier,
    mainModifier: Modifier = Modifier,
    onChangeDisplayMode: () -> Unit,
    onChangeSelectedPlaylistIndex: (Int) -> Unit,
    onChangeSongMenuIndex: (Int) -> Unit,
    onDeleteSongFromPlaylist: (Long) -> Unit
) {
    HeaderListSongs(
        playlistName = playlistName,
        iconDisplayMode = iconDisplayMode,
        modifier = headerModifier,
        onChangeDisplayMode = { onChangeDisplayMode() },
        onChangeSelectedPlaylistIndex = { onChangeSelectedPlaylistIndex(-1) }
    )

    if (isListLayout) {
        ListSongs(
            songs = songs,
            songMenuIndex = songMenuIndex,
            modifier = mainModifier,
            onChangeSongMenuIndex = { onChangeSongMenuIndex(it) },
            onDeleteSongFromPlaylist = { onDeleteSongFromPlaylist(it) }
        )
    } else {
        GridSongs(
            songs = songs,
            songMenuIndex = songMenuIndex,
            onChangeSongMenuIndex = { onChangeSongMenuIndex(it) },
            onDeleteSongFromPlaylist = { onDeleteSongFromPlaylist(it) }
        )
    }
}


@Composable
fun HeaderListSongs(
    playlistName: String,
    iconDisplayMode: Int,
    modifier: Modifier = Modifier,
    onChangeDisplayMode: () -> Unit,
    onChangeSelectedPlaylistIndex: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        IconButton(
            onClick = onChangeSelectedPlaylistIndex, //Go back to playlist layout
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
            text = playlistName,
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
    songMenuIndex: Int,
    modifier: Modifier = Modifier,
    onChangeSongMenuIndex: (Int) -> Unit,
    onDeleteSongFromPlaylist: (Long) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
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
                            onClick = { onChangeSongMenuIndex(index) }, // On show menu
                            modifier = Modifier.size(35.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_menu),
                                contentDescription = "Menu",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        if (songMenuIndex == index) {
                            SongDropDownMenu(
                                expanded = true,
                                onDismissRequest = { onChangeSongMenuIndex(-1) },
                                onDelete = { onDeleteSongFromPlaylist(songs[index].songId) },
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
    songMenuIndex: Int,
    modifier: Modifier = Modifier,
    onChangeSongMenuIndex: (Int) -> Unit,
    onDeleteSongFromPlaylist: (Long) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    LazyVerticalGrid(
        columns = GridCells.Fixed((screenWidth.value / 200.dp.value).toInt()),
        modifier = modifier.fillMaxWidth(),
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
                        onClick = { onChangeSongMenuIndex(index) },
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

                    if (songMenuIndex == index) {
                        SongDropDownMenu(
                            expanded = true,
                            onDismissRequest = { onChangeSongMenuIndex(-1) },
                            onDelete = { onDeleteSongFromPlaylist(songs[index].songId) },
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