package com.example.dinhngocthe.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.dinhngocthe.R
import com.example.dinhngocthe.data.remote.model.TopArtistResponse

@Composable
fun TopArtist(
    modifier: Modifier = Modifier,
    topArtist: TopArtistResponse?
) {
    LazyRow(
        modifier = modifier.fillMaxWidth().padding(start = 15.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(5) { index ->
            Box(
                modifier = Modifier.size(180.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(
                            topArtist?.artists
                                ?.artist
                                ?.getOrNull(index)
                                ?.image
                                ?.find { it.size == "large" }
                                ?.text.orEmpty()
                        )
                        .error(R.drawable.img_top_artist_default)
                        .size(500)
                        .build(),
                    contentDescription = "Track image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(180.dp)
                )

                Text(
                    text = topArtist?.artists
                        ?.artist
                        ?.getOrNull(index)
                        ?.name ?: "",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.primary
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 15.dp, start = 15.dp)
                )
            }
        }
    }
}

//@Preview
//@Composable
//private fun TopArtistPreview() {
//    TopArtist()
//}