package com.example.dinhngocthe.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dinhngocthe.R
import com.example.dinhngocthe.ui.theme.AppFonts

@Composable
fun MenuSongDropDown(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onRemove: () -> Unit,
    onShare: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = Modifier.background(colorResource(R.color.brown)).padding(start = 5.dp, end = 5.dp)
    ) {
        // Remove
        DropdownMenuItem(
            text = {
                Text(
                    text = "Remove from playlist",
                    fontFamily = AppFonts.mainFontBold,
                    fontSize = 13.sp,
                    color = Color.White
                )
            },
            onClick = {
                onRemove()
                onDismissRequest()
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_remove_song),
                    contentDescription = "Remove",
                    modifier = Modifier.size(20.dp),
                    tint = Color.Unspecified
                )
            }
        )

        // Share
        DropdownMenuItem(
            text = {
                Text(
                    text = "Share (coming soon)",
                    fontFamily = AppFonts.mainFontBold,
                    fontSize = 13.sp,
                    color = Color.White
                )
            },
            onClick = {
                onShare()
                onDismissRequest()
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_share_song),
                    contentDescription = "Share",
                    modifier = Modifier.size(20.dp),
                    tint = Color.Unspecified
                )
            }
        )
    }
}

@Preview
@Composable
private fun preview() {
    MenuSongDropDown(true, {true}, {}, {})
}