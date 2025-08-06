package com.example.dinhngocthe.presentation.profile

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.dinhngocthe.R

@Composable
fun Header(
    isEditingMode: Boolean,
    icChangeMode: Int,
    avatarUri: Uri?,
    modifier: Modifier = Modifier,
    onEdit: () -> Unit,
    onChangeMode: () -> Unit,
    onChangePicture: () -> Unit,

) {
    val density = LocalDensity.current
    val imageSizePx = with(density) { 150.dp.roundToPx() }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        IconButton(
            onClick = { onChangeMode() },
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(50.dp)
                .padding(10.dp)
        ) {
            Icon(
                painter = painterResource(icChangeMode),
                contentDescription = "Switch display mode",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxSize()
            )
        }

        Text(
            text = "MY INFORMATION",
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 10.dp),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )

        if (!isEditingMode) {
            IconButton(
                onClick = { onEdit() },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(50.dp)
                    .padding(5.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = "Edit Profile",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(20.dp))

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(170.dp)
    ) {
        if (avatarUri != null) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(avatarUri)
                    .size(imageSizePx)
                    .crossfade(true)
                    .allowHardware(false)
                    .build(),
                contentDescription = "Avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .size(150.dp)
                    .border(2.dp, MaterialTheme.colorScheme.primary, shape = CircleShape)
                    .clip(CircleShape),
                error = painterResource(R.drawable.img_avatar)
            )
        } else {
            Image(
                painterResource(R.drawable.img_avatar),
                contentDescription = "Avatar",
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .size(150.dp)
                    .border(2.dp, MaterialTheme.colorScheme.primary, shape = CircleShape)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
            )
        }

        if (isEditingMode) {
            Button(
                onClick = { onChangePicture() },
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .align(Alignment.BottomCenter),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_choose_picture),
                    contentDescription = "Press to change avatar",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(30.dp))
}