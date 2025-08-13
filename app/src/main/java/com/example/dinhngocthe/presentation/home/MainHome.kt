package com.example.dinhngocthe.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dinhngocthe.R
import com.example.dinhngocthe.data.remote.model.TopAlbumsResponse

@Composable
fun MainHome(
    modifier: Modifier = Modifier,
    topAlbums: TopAlbumsResponse?
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        item {
            Row(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_ranking),
                    contentDescription = "Ranking",
                    modifier = Modifier
                        .size(32.dp)
                        .padding(bottom = 5.dp)
                        .align(Alignment.Bottom),
                    tint = colorResource(R.color.gold)
                )

                Spacer(modifier = Modifier.size(5.dp))

                Text(
                    text = "Ranking",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.align(Alignment.Bottom)
                )
            }
            Spacer(modifier = Modifier.size(10.dp))
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Text(
                    text = "Top Albums",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                Text(
                    text = "See all",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline
                    ),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .clip(RoundedCornerShape(6.dp))
                        .clickable{  }
                )
            }
        }

        item {
            TopAlbums(
                topAlbums = topAlbums
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun PrevMainHome() {
//    LazyColumn(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(MaterialTheme.colorScheme.surface)
//    ) {
//        item {
//            Row(
//                modifier = Modifier.padding(horizontal = 20.dp)
//            ) {
//                Icon(
//                    painter = painterResource(R.drawable.ic_ranking),
//                    contentDescription = "Ranking",
//                    modifier = Modifier
//                        .size(32.dp)
//                        .padding(bottom = 5.dp)
//                        .align(Alignment.Bottom),
//                    tint = colorResource(R.color.gold)
//                )
//
//                Spacer(modifier = Modifier.size(5.dp))
//
//                Text(
//                    text = "Ranking",
//                    style = MaterialTheme.typography.titleLarge.copy(
//                        color = MaterialTheme.colorScheme.primary
//                    ),
//                    modifier = Modifier.align(Alignment.Bottom)
//                )
//            }
//            Spacer(modifier = Modifier.size(10.dp))
//        }
//
//        item {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 20.dp)
//            ) {
//                Text(
//                    text = "Top Albums",
//                    style = MaterialTheme.typography.titleLarge.copy(
//                        color = MaterialTheme.colorScheme.onSurface
//                    ),
//                    modifier = Modifier.align(Alignment.CenterStart)
//                )
//                Text(
//                    text = "See all",
//                    style = MaterialTheme.typography.labelSmall.copy(
//                        color = MaterialTheme.colorScheme.primary,
//                        textDecoration = TextDecoration.Underline
//                    ),
//                    modifier = Modifier
//                        .align(Alignment.BottomEnd)
//                        .clip(RoundedCornerShape(6.dp))
//                        .clickable{  }
//                )
//            }
//        }
//
//        item {
//            TopAlbums()
//        }
//    }
//}