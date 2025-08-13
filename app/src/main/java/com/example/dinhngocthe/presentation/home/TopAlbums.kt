package com.example.dinhngocthe.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.dinhngocthe.R
import com.example.dinhngocthe.data.remote.model.TopAlbumsResponse

@Composable
fun TopAlbums(
    modifier: Modifier = Modifier,
    topAlbums: TopAlbumsResponse?
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
    ) {
        if (topAlbums != null) {
            topAlbums.topAlbums.album.chunked(2).forEach { albumDtos ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    albumDtos.forEach { albumDto ->
                        val imageUrl = albumDto.image.find { it.size == "large" }?.text ?: ""

                        Row(
                            modifier = Modifier.weight(1f)
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(imageUrl)
                                    .size(200)
                                    .build(),
                                contentDescription = "Album image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .height(70.dp)
                                    .clip(RoundedCornerShape(10.dp)),
                                error = painterResource(R.drawable.img_top_albums_default)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Column {
                                Text(
                                    text = albumDto.name,
                                    style = MaterialTheme.typography.titleLarge,
                                    maxLines = 1
                                )
                                Text(
                                    text = albumDto.artist.name,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.Gray,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun PrevTopAlbums() {
//    TopAlbums()
//}