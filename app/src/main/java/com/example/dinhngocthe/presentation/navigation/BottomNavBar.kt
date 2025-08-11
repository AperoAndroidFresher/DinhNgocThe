package com.example.dinhngocthe.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

data class BottomNavBarItem(
    val id: Int,
    val name: String,
    val icon: Int,
    val route: Destination
)

@Composable
fun BottomNavBar(
    currentRoute: Destination,
    onItemClick: (Destination) -> Unit,
    items: List<BottomNavBarItem>
) {
    NavigationBar(
        windowInsets = NavigationBarDefaults.windowInsets,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
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

