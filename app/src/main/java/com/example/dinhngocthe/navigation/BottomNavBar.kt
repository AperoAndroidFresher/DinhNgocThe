package com.example.dinhngocthe.navigation

import androidx.compose.material3.Icon
import com.example.dinhngocthe.navigation.Destination
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.dinhngocthe.R

@Composable
fun BottomNavBar(
    currentRoute: Destination,
    onItemClick: (Destination) -> Unit,
    items: List<BottomNavBarItem>
) {
    NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = item.route == currentRoute,
                onClick = { onItemClick(item.route) },
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = item.name
                    )
                },
                label = { Text(item.name) }
            )
        }
    }
}

data class BottomNavBarItem(
    val id: Int,
    val name: String,
    val icon: Int,
    val route: Destination
)

