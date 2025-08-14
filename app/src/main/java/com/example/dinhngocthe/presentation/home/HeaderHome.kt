package com.example.dinhngocthe.presentation.home

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.dinhngocthe.R
import com.example.dinhngocthe.data.local.entities.User

@Composable
fun HeaderHome(
    user: User,
    modifier: Modifier = Modifier,
    navigateToProfileScreen: () -> Unit,
    navigateToSettingScreen: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.CenterStart)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(user.avatarUri)
                    .size(150)
                    .crossfade(true)
                    .allowHardware(false)
                    .build(),
                contentDescription = "Avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(50.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary, shape = CircleShape)
                    .clip(CircleShape)
                    .clickable { navigateToProfileScreen() },
                error = painterResource(R.drawable.img_avatar)
            )
            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 10.dp)
            ) {
                Text(
                    text = "Welcome back!",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 18.sp
                    )
                )
                Text(
                    text = user.fullName,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(
                            alpha = 0.5f
                        )
                    )
                )
            }
        }

        IconButton(
            onClick = { navigateToSettingScreen() },
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(50.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_setting),
                contentDescription = "Go Profile",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .size(50.dp)
                    .padding(12.dp)
            )
        }
    }

    Spacer(Modifier.size(15.dp))
}

@Preview(showBackground = true)
@Composable
private fun PrevHeaderHome() {

}