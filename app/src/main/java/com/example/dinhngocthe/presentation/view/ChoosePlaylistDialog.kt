package com.example.dinhngocthe.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.dinhngocthe.R
import com.example.dinhngocthe.data.room.entities.Playlist
import com.example.dinhngocthe.presentation.theme.AppFonts

@Composable
fun ChoosePlaylistDialog(
    playlists: List<Playlist>,
    onDismiss: () -> Unit,
    onSelectPlaylist: (Int) -> Unit,
    navigateToPlaylist: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .size(350.dp, 450.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(colorResource(com.example.dinhngocthe.R.color.brown))
                .padding(vertical = 20.dp)
        ) {
            Text(
                text = "Choose Playlist",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(Modifier.size(20.dp))

            if (playlists.isEmpty()) {
                AddPlaylist(
                    navigateToPlaylist = { navigateToPlaylist() }
                )
            } else {
                ListPlaylists(
                    playlists,
                    onSelectPlaylist = { onSelectPlaylist(it) }
                )
            }
        }
    }
}

@Composable
fun AddPlaylist(
    navigateToPlaylist: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 50.dp, end = 50.dp, bottom = 30.dp),
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
                onClick = { navigateToPlaylist() },
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

@Composable
fun ListPlaylists(
    playlists: List<Playlist>,
    onSelectPlaylist: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(playlists.size) { index ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { onSelectPlaylist(index) }
                    .padding(horizontal = 20.dp, vertical = 5.dp)

            ) {
                Row {
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(playlists[index].coverArt)
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
                            playlists[index].name,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            playlists[index].numberSong.toString() + " songs",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 12.sp)
                        )
                    }
                }
            }
        }
    }
}
