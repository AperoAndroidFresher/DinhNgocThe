package com.example.dinhngocthe.presentation.musicplayer

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.example.dinhngocthe.utils.formatDuration
import com.smarttoolfactory.slider.ColorfulSlider
import com.smarttoolfactory.slider.MaterialSliderDefaults
import com.smarttoolfactory.slider.SliderBrushColor

@Composable
fun MainMusicPlayer(
    progress: Float,
    duration: Long,
    coverArtUri: Uri?,
    songName: String,
    singer: String,
    iconPlayPause: Int,
    enableShuffle: Boolean,
    enablePrevious: Boolean,
    enableNext: Boolean,
    enableRepeat: Boolean,
    modifier: Modifier = Modifier,
    onPlayPause: () -> Unit,
    onNextMusic: () -> Unit,
    onPreviousMusic: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 40.dp, end = 40.dp, top = 20.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(coverArtUri)
                .build(),
            contentDescription = "Cover art",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(shape = RoundedCornerShape(10.dp)),
            error = painterResource(R.drawable.img_song_default)
        )

        Spacer(Modifier.size(15.dp))

        Text(
            text = songName,
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 20.sp
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 3.dp)
        )
        Text(
            text = singer,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 3.dp)
        )

        Spacer(Modifier.size(20.dp))

        Box(
            modifier = Modifier.fillMaxWidth().height(55.dp)
        ) {
            ColorfulSlider(
                value = progress,
                valueRange = 0f..1f,
                onValueChange = {                       },
                thumbRadius = 7.dp,
                trackHeight = 5.dp,
                colors = MaterialSliderDefaults.customColors(
                    thumbColor = SliderBrushColor(MaterialTheme.colorScheme.primary),
                    disabledThumbColor = SliderBrushColor(MaterialTheme.colorScheme.primary),
                    activeTrackColor = SliderBrushColor(MaterialTheme.colorScheme.primary),
                    disabledActiveTrackColor = SliderBrushColor(MaterialTheme.colorScheme.primary),
                    inactiveTrackColor = SliderBrushColor(MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)),
                    disabledInactiveTrackColor = SliderBrushColor(MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)),
                    activeTickColor = SliderBrushColor(Color.White),
                    inactiveTickColor = SliderBrushColor(Color.White),
                    disabledActiveTickColor = SliderBrushColor(Color.White),
                    disabledInactiveTickColor = SliderBrushColor(Color.White)
                ),
                modifier = Modifier.align(Alignment.TopCenter)
            )

            Text(
                text = formatDuration((duration * progress).toLong()),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.BottomStart).padding(start = 6.dp)
            )
            Text(
                text = formatDuration(duration),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.BottomEnd).padding(end = 6.dp)
            )
        }

        Spacer(Modifier.size(10.dp))

        // Controller
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = { },
                modifier = Modifier.weight(1f),
                enabled = enableShuffle
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_shuffle),
                    contentDescription = "Press to shuffle"
                )
            }

            IconButton(
                onClick = { onPreviousMusic() },
                modifier = Modifier.weight(1f),
                enabled = enablePrevious
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_previous_music),
                    contentDescription = "Press to previous music"
                )
            }

            IconButton(
                onClick = { onPlayPause() },
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(43.dp)
                    .clip(shape = CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
            ) {
                Icon(
                    painter = painterResource(iconPlayPause),
                    contentDescription = "Press to play or pause",
                    modifier = Modifier
                        .size(20.dp),
                    tint = Color.White
                )
            }

            IconButton(
                onClick = { onNextMusic() },
                modifier = Modifier.weight(1f),
                enabled = enableNext
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_next_music),
                    contentDescription = "Press to next music"
                )
            }

            IconButton(
                onClick = { },
                modifier = Modifier.weight(1f),
                enabled = enableRepeat
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_repeat),
                    contentDescription = "Press to repeat"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PrevMainMusicPlayerScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().padding(horizontal = 40.dp, vertical = 10.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.img_song_default),
            contentDescription = "Cover art",
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop,
        )

        Spacer(Modifier.size(20.dp))

        Text(
            text = "Nắng Có Mang Em Về",
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 20.sp
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 10.dp)
        )
        Text(
            text = "Phankeoo",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 10.dp)
        )

        Spacer(Modifier.size(20.dp))

        Box(
            modifier = Modifier.fillMaxWidth().height(55.dp)
        ) {
            ColorfulSlider(
                value = 0.3f,
                valueRange = 0f..1f,
                onValueChange = { },
                thumbRadius = 7.dp,
                trackHeight = 5.dp,
                colors = MaterialSliderDefaults.customColors(
                    thumbColor = SliderBrushColor(MaterialTheme.colorScheme.primary),
                    disabledThumbColor = SliderBrushColor(MaterialTheme.colorScheme.primary),
                    activeTrackColor = SliderBrushColor(MaterialTheme.colorScheme.primary),
                    disabledActiveTrackColor = SliderBrushColor(MaterialTheme.colorScheme.primary),
                    inactiveTrackColor = SliderBrushColor(MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)),
                    disabledInactiveTrackColor = SliderBrushColor(MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)),
                    activeTickColor = SliderBrushColor(Color.White),
                    inactiveTickColor = SliderBrushColor(Color.White),
                    disabledActiveTickColor = SliderBrushColor(Color.White),
                    disabledInactiveTickColor = SliderBrushColor(Color.White)
                ),
                modifier = Modifier.align(Alignment.TopCenter)
            )

            Text(
                text = "2:38",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.BottomStart).padding(start = 6.dp)
            )
            Text(
                text = "4:38",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.BottomEnd).padding(end = 6.dp)
            )
        }

        Spacer(Modifier.size(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = { },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_shuffle),
                    contentDescription = "Press to shuffle"
                )
            }

            IconButton(
                onClick = { },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_previous_music),
                    contentDescription = "Press to previous music"
                )
            }

            IconButton(
                onClick = {},
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(43.dp)
                    .clip(shape = CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_play),
                    contentDescription = "Press to play or pause",
                    modifier = Modifier
                        .padding(start = 2.dp)
                        .size(20.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }

            IconButton(
                onClick = { },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_next_music),
                    contentDescription = "Press to next music"
                )
            }

            IconButton(
                onClick = { },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_repeat),
                    contentDescription = "Press to repeat"
                )
            }
        }
    }
}