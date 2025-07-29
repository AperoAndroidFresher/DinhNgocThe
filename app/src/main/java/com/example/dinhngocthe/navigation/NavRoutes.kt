package com.example.dinhngocthe.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.dinhngocthe.R
import com.example.dinhngocthe.ui.view.HomeScreen
import com.example.dinhngocthe.ui.view.LibraryScreen
import com.example.dinhngocthe.ui.view.LoginScreen
import com.example.dinhngocthe.ui.view.MyPlaylistScreen
import com.example.dinhngocthe.ui.view.ProfileScreen
import com.example.dinhngocthe.ui.view.SignUpScreen

@Composable
fun NavRoutes(
    onChangeMode: () -> Unit,
    isDarkTheme: Boolean
) {
    var backStack = rememberNavBackStack(Destination.LoginRoute)
    val currentRoute = backStack.lastOrNull() as Destination
    val items = listOf<BottomNavBarItem>(
        BottomNavBarItem(0, "Home", R.drawable.ic_home, Destination.HomeRoute),
        BottomNavBarItem(1, "Library", R.drawable.ic_library, Destination.LibraryRoute),
        BottomNavBarItem(2, "Playlist", R.drawable.ic_playlist, Destination.MyPlaylistRoute),
    )

    Scaffold(
        bottomBar = {
            if (currentRoute in items.map { it.route }) {
                BottomNavBar(
                    currentRoute = currentRoute,
                    onItemClick = { destination ->
                        if (destination != backStack.lastOrNull()) {
                            backStack.clear()
                            backStack.add(destination)
                        }
                    },
                    items
                )
            }
        }
    ) { innerPadding ->

        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryProvider = entryProvider {
                entry<Destination.LoginRoute> {
                    LoginScreen(
                        innerPadding = innerPadding,
                        onSignUp = { backStack.add(Destination.SignUpRoute) },
                        loginSuccess = {
                            backStack.clear()
                            backStack.add(Destination.HomeRoute)
                        }
                    )
                }

                entry<Destination.SignUpRoute> {
                    SignUpScreen(
                        innerPadding = innerPadding,
                        signUpSuccess = { backStack.removeLastOrNull() },
                        onBack = { backStack.removeLastOrNull() }
                    )
                }

                entry<Destination.HomeRoute> {
                    HomeScreen(
                        innerPadding = innerPadding,
                        goProfile = {
                            backStack.add(Destination.ProfileRoute)
                        }
                    )
                }

                entry<Destination.ProfileRoute> {
                    ProfileScreen(
                        onChangeMode = onChangeMode,
                        isDarkTheme,
                        innerPadding = innerPadding
                    )
                }

                entry<Destination.LibraryRoute> {
                    LibraryScreen(
                        innerPadding = innerPadding
                    )
                }

                entry<Destination.MyPlaylistRoute> {
                    MyPlaylistScreen(innerPadding = innerPadding)
                }
            }
        )
    }
}