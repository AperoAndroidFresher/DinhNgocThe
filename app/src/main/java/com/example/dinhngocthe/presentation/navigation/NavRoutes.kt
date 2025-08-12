package com.example.dinhngocthe.presentation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.dinhngocthe.R
import com.example.dinhngocthe.presentation.home.HomeScreen
import com.example.dinhngocthe.presentation.library.LibraryScreen
import com.example.dinhngocthe.presentation.login.LoginScreen
import com.example.dinhngocthe.presentation.miniplayer.MiniPlayer
import com.example.dinhngocthe.presentation.musicplayer.MusicPlayerScreen
import com.example.dinhngocthe.presentation.playlist.MyPlaylistScreen
import com.example.dinhngocthe.presentation.profile.ProfileScreen
import com.example.dinhngocthe.presentation.signup.SignUpScreen

@Composable
fun NavRoutes(
    onChangeMode: () -> Unit,
    isDarkTheme: Boolean,
    isLogged: Boolean,
) {
    val firstRoute = if (isLogged) Destination.HomeRoute else Destination.LoginRoute
    var backStack = rememberNavBackStack(firstRoute)
    val currentRoute = backStack.lastOrNull() as Destination
    val items = listOf<BottomNavBarItem>(
        BottomNavBarItem(0, "Home", R.drawable.ic_home, Destination.HomeRoute),
        BottomNavBarItem(1, "Library", R.drawable.ic_library, Destination.LibraryRoute),
        BottomNavBarItem(2, "Playlist", R.drawable.ic_playlist, Destination.PlaylistRoute),
    )

    Scaffold(
        bottomBar = {
            if (currentRoute in items.map { it.route }) {
                Column {
                    MiniPlayer(
                        navigateToMusicPlayerScreen = { backStack.add(Destination.MusicPlayerRoute) }
                    )

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
                        onBack = { backStack.removeLastOrNull() }
                    )
                }

                entry<Destination.HomeRoute> {
                    HomeScreen(
                        innerPadding = innerPadding,
                        navigateToProfileScreen = {
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
                        innerPadding = innerPadding,
                        navigateToPlaylist = {
                            backStack.clear()
                            backStack.add(Destination.PlaylistRoute)
                        }
                    )
                }

                entry<Destination.PlaylistRoute> {
                    MyPlaylistScreen(innerPadding = innerPadding)
                }

                entry<Destination.MusicPlayerRoute> {
                    MusicPlayerScreen(
                        innerPadding = innerPadding,
                        onBack = { backStack.removeLastOrNull() }
                    )
                }
            }
        )
    }
}