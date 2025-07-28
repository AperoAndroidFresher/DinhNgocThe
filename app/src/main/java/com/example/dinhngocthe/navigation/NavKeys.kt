package com.example.dinhngocthe.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Destination : NavKey {
    @Serializable
    data object LoginRoute : Destination

    @Serializable
    data object SignUpRoute: Destination

    @Serializable
    data object MyPlaylistRoute: Destination

    @Serializable
    data object HomeRoute: Destination

    @Serializable
    data object LibraryRoute: Destination

    @Serializable
    data object ProfileRoute: Destination
}
