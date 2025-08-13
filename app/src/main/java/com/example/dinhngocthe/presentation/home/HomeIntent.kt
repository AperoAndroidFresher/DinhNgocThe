package com.example.dinhngocthe.presentation.home

sealed interface HomeIntent {
    data object LoadData : HomeIntent
}