package com.example.dinhngocthe.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.dinhngocthe.R
@Composable
fun PlaylistDialog(
    onDismiss: () -> Unit,
    playlistAction: (String) -> Unit,
    actionName: String = "Create",
    title: String = "New Playlist"
){
    Dialog(
        onDismissRequest = onDismiss
    ) {
        var name by remember { mutableStateOf("") }
        var placeholder by remember { mutableStateOf("Playlist name") }

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(colorResource(R.color.brown))
                .padding(top = 20.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            TextField(
                value = name,
                onValueChange = { if(name.length <= 20) name = it },
                modifier = Modifier.size(width = 280.dp, 60.dp).align(Alignment.CenterHorizontally),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorResource(R.color.brown),
                    unfocusedContainerColor = colorResource(R.color.brown),
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White,
                    cursorColor = Color.White,
                ),
                textStyle = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 18.sp
                ),
                placeholder = { Text(
                    text = placeholder,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = Color.Gray,
                        fontSize = 18.sp
                    )
                ) }
            )
            Box(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .width(340.dp)
                    .height(1.dp)
                    .background(Color.Gray)
            )
            Row(modifier = Modifier.width(340.dp)) {
                TextButton(
                    onClick = { onDismiss() },
                    modifier = Modifier.clip(RoundedCornerShape(0.dp)).weight(1f).height(50.dp)
                ) {
                    Text(
                        text = "Cancel",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }
                TextButton(
                    onClick = {
                        if (name != "") {
                            playlistAction(name)
                            onDismiss()
                        } else placeholder = "Enter playlist name!"
                    },
                    modifier = Modifier.clip(RoundedCornerShape(0.dp)).weight(1f).height(50.dp)
                ) {
                    Text(
                        text = actionName,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AddPlaylistPrev() {
    var name by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(colorResource(R.color.brown))
            .padding(top = 15.dp, bottom = 15.dp)
    ) {
        Text(
            text = "New Playlist",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        TextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier.size(width = 300.dp, height = 50.dp).align(Alignment.CenterHorizontally),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colorResource(R.color.brown),
                unfocusedContainerColor = colorResource(R.color.brown),
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.White,
                cursorColor = Color.White,
            ),
            textStyle = MaterialTheme.typography.labelSmall.copy(
                color = MaterialTheme.colorScheme.onSurface
            )
        )
        Box(
            modifier = Modifier
                .padding(top = 20.dp)
                .width(340.dp)
                .height(1.dp)
                .background(Color.Gray)
        )
        Row(modifier = Modifier.width(340.dp)) {
            TextButton(
                onClick = {},
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Cancel",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }
            TextButton(
                onClick = {},
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Create",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }
    }
}