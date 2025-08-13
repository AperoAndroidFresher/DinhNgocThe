package com.example.dinhngocthe.di

import android.app.Application
import com.example.dinhngocthe.data.local.datasource.DeviceSongDataSource
import com.example.dinhngocthe.data.local.datasource.DeviceSongDataSourceImpl
import com.example.dinhngocthe.data.local.datasource.DownloadSongDataSource
import com.example.dinhngocthe.data.local.datasource.DownloadSongDataSourceImpl
import com.example.dinhngocthe.data.local.datastore.UserDataStore
import com.example.dinhngocthe.data.repository.HomeRepositoryImpl
import com.example.dinhngocthe.data.repository.PlaylistRepositoryImpl
import com.example.dinhngocthe.data.repository.SongRepositoryImpl
import com.example.dinhngocthe.data.repository.UserRepositoryImpl
import com.example.dinhngocthe.domain.repository.HomeRepository
import com.example.dinhngocthe.domain.repository.PlaylistRepository
import com.example.dinhngocthe.domain.repository.SongRepository
import com.example.dinhngocthe.domain.repository.UserRepository
import com.example.dinhngocthe.presentation.home.HomeViewModel
import com.example.dinhngocthe.presentation.library.LibraryViewModel
import com.example.dinhngocthe.presentation.login.LoginViewModel
import com.example.dinhngocthe.presentation.miniplayer.MiniPlayerViewModel
import com.example.dinhngocthe.presentation.musicplayer.MusicPlayerViewModel
import com.example.dinhngocthe.presentation.playlist.PlaylistViewModel
import com.example.dinhngocthe.presentation.profile.ProfileViewModel
import com.example.dinhngocthe.presentation.signup.SignUpViewModel
import com.example.dinhngocthe.presentation.splash.SplashViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Data Store
    single<UserDataStore> { UserDataStore(get()) }

    // DataSources
    single<DeviceSongDataSource> { DeviceSongDataSourceImpl() }
    single<DownloadSongDataSource> { DownloadSongDataSourceImpl() }

    // Repositories
    single<UserRepository> { UserRepositoryImpl(get<Application>()) }
    single<SongRepository> {
        SongRepositoryImpl(
            context = get<Application>(),
            deviceSongDataSource = get(),
            downloadSongDataSource = get()
        )
    }
    single<PlaylistRepository> { PlaylistRepositoryImpl(get<Application>()) }
    single<HomeRepository> { HomeRepositoryImpl() }

    // ViewModels
    viewModel { LoginViewModel(get(), get()) }
    viewModel { LibraryViewModel(get(),get(), get(), get()) }
    viewModel { PlaylistViewModel(get(), get()) }
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { MiniPlayerViewModel() }
    viewModel { MusicPlayerViewModel() }
    viewModel { SplashViewModel(get()) }
    viewModel { HomeViewModel(get(), get(), get()) }
}

