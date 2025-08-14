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
import com.example.dinhngocthe.data.remote.model.ArtistDto
import com.example.dinhngocthe.data.remote.model.ImageDto
import com.example.dinhngocthe.data.remote.model.TopTracksDto
import com.example.dinhngocthe.data.remote.model.TrackDto

@Composable
fun TopTracks(
    topTracks: TopTracksDto?,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth().padding(start = 15.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(5) { index ->
            Box(
                modifier = Modifier.size(150.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(
                            topTracks?.track
                                ?.getOrNull(index)
                                ?.image
                                ?.find { it.size == "large" }
                                ?.text.orEmpty()
                        )
                        .error(R.drawable.img_top_tracks_default)
                        .size(400)
                        .build(),
                    contentDescription = "Track image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(150.dp)
                )

                Text(
                    text = topTracks?.track?.getOrNull(index)?.name ?: "",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.primary
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 10.dp, start = 10.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                ) {
                    Row(
                        modifier = Modifier.padding(start = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_play_count),
                            contentDescription = "Icon play count",
                            modifier = Modifier.size(15.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )

                        Spacer(Modifier.size(7.dp))

                        Text(
                            text = topTracks?.track?.getOrNull(index)?.playcount.toString(),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    }

                    Spacer(Modifier.size(7.dp))

                    Row(
                        modifier = Modifier.padding(start = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_artist),
                            contentDescription = "Icon play count",
                            modifier = Modifier.size(15.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )

                        Spacer(Modifier.size(7.dp))

                        Text(
                            text = topTracks?.track?.getOrNull(index)?.artist?.name ?: "",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.primary
                            ),
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Spacer(Modifier.size(10.dp))

                    Row(
                        modifier = Modifier
                            .size(150.dp, 9.dp)
                            .background(TopTrackColors.topTrackColors[index % 7])
                    ) {

                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PrevTopTrack() {
    val fakeTopTracks = TopTracksDto(
        track = listOf(
            TrackDto(
                name = "Anomaly [Calling Your Name] [Ferry Corsten Remix]",
                playcount = "9243",
                artist = ArtistDto(name = "Taylor"),
                image = listOf(
                    ImageDto("https://lastfm.freetls.fastly.net/i/u/34s/img1.png", "small"),
                    ImageDto("https://lastfm.freetls.fastly.net/i/u/64s/img1.png", "medium"),
                    ImageDto("https://lastfm.freetls.fastly.net/i/u/174s/img1.png", "large"),
                    ImageDto("https://lastfm.freetls.fastly.net/i/u/300x300/img1.png", "extralarge")
                )
            ),
            TrackDto(
                name = "Silence [DJ Tiësto In Search of Sunrise Remix]",
                playcount = "15000",
                artist = ArtistDto(name = "Delerium"),
                image = listOf(
                    ImageDto("https://lastfm.freetls.fastly.net/i/u/34s/img2.png", "small"),
                    ImageDto("https://lastfm.freetls.fastly.net/i/u/64s/img2.png", "medium"),
                    ImageDto("https://lastfm.freetls.fastly.net/i/u/174s/img2.png", "large"),
                    ImageDto("https://lastfm.freetls.fastly.net/i/u/300x300/img2.png", "extralarge")
                )
            ),
            TrackDto(
                name = "1998 [Paul van Dyk Remix]",
                playcount = "10345",
                artist = ArtistDto(name = "Binary Finary"),
                image = listOf(
                    ImageDto("https://lastfm.freetls.fastly.net/i/u/34s/img3.png", "small"),
                    ImageDto("https://lastfm.freetls.fastly.net/i/u/64s/img3.png", "medium"),
                    ImageDto("https://lastfm.freetls.fastly.net/i/u/174s/img3.png", "large"),
                    ImageDto("https://lastfm.freetls.fastly.net/i/u/300x300/img3.png", "extralarge")
                )
            ),
            TrackDto(
                name = "Carte Blanche",
                playcount = "8765",
                artist = ArtistDto(name = "Veracocha"),
                image = listOf(
                    ImageDto("https://lastfm.freetls.fastly.net/i/u/34s/img4.png", "small"),
                    ImageDto("https://lastfm.freetls.fastly.net/i/u/64s/img4.png", "medium"),
                    ImageDto("https://lastfm.freetls.fastly.net/i/u/174s/img4.png", "large"),
                    ImageDto("https://lastfm.freetls.fastly.net/i/u/300x300/img4.png", "extralarge")
                )
            ),
            TrackDto(
                name = "Southern Sun",
                playcount = "11234",
                artist = ArtistDto(name = "Paul Oakenfold"),
                image = listOf(
                    ImageDto("https://lastfm.freetls.fastly.net/i/u/34s/img5.png", "small"),
                    ImageDto("https://lastfm.freetls.fastly.net/i/u/64s/img5.png", "medium"),
                    ImageDto("https://lastfm.freetls.fastly.net/i/u/174s/img5.png", "large"),
                    ImageDto("https://lastfm.freetls.fastly.net/i/u/300x300/img5.png", "extralarge")
                )
            ),
            TrackDto(
                name = "As the Rush Comes",
                playcount = "9876",
                artist = ArtistDto(name = "Motorcycle"),
                image = listOf(
                    ImageDto("https://lastfm.freetls.fastly.net/i/u/34s/img6.png", "small"),
                    ImageDto("https://lastfm.freetls.fastly.net/i/u/64s/img6.png", "medium"),
                    ImageDto("https://lastfm.freetls.fastly.net/i/u/174s/img6.png", "large"),
                    ImageDto("https://lastfm.freetls.fastly.net/i/u/300x300/img6.png", "extralarge")
                )
            )
        )
    )

    TopTracks(
        topTracks = fakeTopTracks
    )
}