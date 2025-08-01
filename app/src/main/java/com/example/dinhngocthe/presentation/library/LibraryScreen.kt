package com.example.dinhngocthe.presentation.library

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.dinhngocthe.R
import com.example.dinhngocthe.model.Song
import com.example.dinhngocthe.presentation.view.LibraryDropDownMenu
import com.example.dinhngocthe.presentation.view.MenuSongDropDown

@Composable
fun LibraryScreen(
    innerPadding: PaddingValues
) {
    val context = LocalContext.current.applicationContext as Application
    val viewModel: LibraryViewModel = viewModel(
        factory = remember { LibraryViewModel.LibraryViewModelFactory(context) }
    )
    val state by viewModel.state.collectAsStateWithLifecycle()
    //Log.d("LibraryScreen", state.localSongs.size.toString())

    //Set color for 2 button switch mode
    lateinit var localColor: Pair<Color, Color>
    lateinit var remoteColor: Pair<Color, Color>
    if (state.displayMode == "local") {
        localColor = MaterialTheme.colorScheme.primary to MaterialTheme.colorScheme.onPrimary
        remoteColor = MaterialTheme.colorScheme.secondaryContainer to MaterialTheme.colorScheme.onSecondaryContainer
    } else {
        remoteColor = MaterialTheme.colorScheme.primary to MaterialTheme.colorScheme.onPrimary
        localColor = MaterialTheme.colorScheme.secondaryContainer to MaterialTheme.colorScheme.onSecondaryContainer
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
        HeaderLibrary(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onLocalClick = { viewModel.processIntent(LibraryIntent.ToggleLocalButton) },
            onRemoteClick = { viewModel.processIntent(LibraryIntent.ToggleRemoteButton) },
            localColor = localColor,
            remoteColor = remoteColor
        )
        Spacer(Modifier.size(25.dp))
        MainLibrary(
            displayMode = state.displayMode,
            localSongs = state.localSongs,
            remoteSongs = state.remoteSongs,
            expandedIndex = state.menuExpandedIndex,
            onShowMenu = { viewModel.processIntent(LibraryIntent.ShowMenu(it)) },
            onDismissMenu = { viewModel.processIntent(LibraryIntent.DismissMenu) },
            addToPlaylist = { viewModel.processIntent(LibraryIntent.AddToPlaylist) }
        )
    }
}

@Composable
fun HeaderLibrary(
    modifier: Modifier,
    onLocalClick: () -> Unit,
    onRemoteClick: () -> Unit,
    localColor: Pair<Color, Color>,
    remoteColor: Pair<Color, Color>
) {
    Text(
        text = "Library",
        style = MaterialTheme.typography.titleLarge.copy(
            color = MaterialTheme.colorScheme.onSurface
        ),
        modifier = modifier
    )

    Spacer(Modifier.size(30.dp))

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 45.dp, end = 45.dp)
    ) {
        Button(
            onClick = onLocalClick,
            modifier = Modifier
                .weight(1f)
                .height(45.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = localColor.first,
                contentColor = localColor.second
            )
        ) {
            Text(
                "Local",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp)
            )
        }

        Spacer(Modifier.size(30.dp))

        Button(
            onClick = onRemoteClick,
            modifier = Modifier
                .weight(1f)
                .height(45.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = remoteColor.first,
                contentColor = remoteColor.second
            )
        ) {
            Text(
                "Remote",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp)
            )
        }
    }
}

@Composable
fun MainLibrary(
    displayMode: String,
    localSongs: List<Song>,
    remoteSongs: List<Song>,
    expandedIndex: Int,
    onDismissMenu: () -> Unit,
    onShowMenu: (Int) -> Unit,
    addToPlaylist: (Int) -> Unit
) {
    val songs: List<Song> = if (displayMode == "local") localSongs else remoteSongs
    if (!songs.isEmpty()) {
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
                                LibraryDropDownMenu(
                                    expanded = true,
                                    onDismissRequest = onDismissMenu,
                                    addToPlaylist = { addToPlaylist(index) },
                                    onShare = { }
                                )
                            }
                        }
                    }
                }
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



@Preview
@Composable
private fun PreviewLibrary() {
    LibraryScreen(PaddingValues(0.dp))
}