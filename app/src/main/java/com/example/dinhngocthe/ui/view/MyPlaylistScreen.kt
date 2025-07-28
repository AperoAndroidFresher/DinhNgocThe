package com.example.dinhngocthe.ui.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dinhngocthe.R
import com.example.dinhngocthe.model.Song
import com.example.dinhngocthe.ui.theme.AppFonts

@Composable
fun MyPlaylistScreen(innerPadding: PaddingValues) {
    val songs = remember {
        mutableStateListOf(
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



    var displayMode by remember { mutableStateOf(true) } //true is list mode
    var iconButtonChangeMode by remember { mutableIntStateOf(R.drawable.ic_grid_mode) }
    var menuExpandedIndex by remember { mutableIntStateOf(-1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(top = innerPadding.calculateTopPadding(), bottom = innerPadding.calculateBottomPadding())
    ) {
        Header(
            onClick = {
                displayMode = !displayMode
                iconButtonChangeMode = if (displayMode) R.drawable.ic_grid_mode else R.drawable.ic_list_mode
            },
            iconButtonChangeMode
        )

        Spacer(Modifier.size(30.dp))

        //Switch mode
        if (displayMode) ListSongs(
            songs = songs,
            onShowMenu = { index -> menuExpandedIndex = index },
            expandedIndex = menuExpandedIndex,
            onDismissMenu = { menuExpandedIndex = -1 },
            onRemoveSong = { index ->
                songs.removeAt(index)
                menuExpandedIndex = -1
            },
            onShare = {
                menuExpandedIndex = -1
            }
        )
        else GridSongs(
            songs = songs,
            onShowMenu = { index -> menuExpandedIndex = index },
            expandedIndex = menuExpandedIndex,
            onDismissMenu = { menuExpandedIndex = -1},
            onRemove = { index ->
                songs.removeAt(index)
                menuExpandedIndex = -1
            },
            onShare = {
                menuExpandedIndex = -1
            }
        )
    }
}

@Composable
fun Header(onClick: () -> Unit, iconButtonChangeMode: Int) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            "My Playlist",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 5.dp)
        )

        Row(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 10.dp)
        ) {
            IconButton(
                onClick = { onClick() },
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Icon(
                    painter = painterResource(iconButtonChangeMode),
                    contentDescription = "Press to switch to display modes",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(20.dp)
                )
            }

            IconButton(
                onClick = {},
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_sort),
                    contentDescription = "Press to sort",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .size(25.dp)
                        .padding(top = 1.dp)
                )
            }
        }
    }
}

@Composable
fun ListSongs(
    songs: SnapshotStateList<Song>,
    onShowMenu: (Int) -> Unit,
    expandedIndex: Int,
    onDismissMenu: () -> Unit,
    onRemoveSong: (Int) -> Unit,
    onShare: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(songs.size) { index ->
            Box(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Image(
                        painterResource(songs[index].coverArt),
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
                        modifier = Modifier.padding(top = 2.dp, end = 10.dp)
                    )

                    Box {
                        IconButton(
                            onClick = { onShowMenu(index) },
                            modifier = Modifier.size(35.dp).padding(10.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.icon_menu),
                                contentDescription = "Menu",
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.fillMaxSize()
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
    songs: SnapshotStateList<Song>,
    onShowMenu: (Int) -> Unit,
    expandedIndex: Int,
    onDismissMenu: () -> Unit,
    onRemove: (Int) -> Unit,
    onShare: (Int) -> Unit
    ) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(songs.size) {index ->
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier.fillMaxWidth().padding(start = 30.dp, end = 30.dp)) {
                    Image(
                        painter = painterResource(songs[index].coverArt),
                        contentDescription = "Cover art",
                        modifier = Modifier.clip(RoundedCornerShape(7.dp))
                    )
                    IconButton(
                        onClick = { onShowMenu(index) },
                        modifier = Modifier.align(Alignment.TopEnd).size(38.dp).padding(5.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_menu_round),
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
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 18.sp)
                )
                Text(
                    text = songs[index].singers,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelSmall
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

//@Preview(showBackground = true)
//@Composable
//private fun preview() {
//    MyPlaylistScreen()
//}