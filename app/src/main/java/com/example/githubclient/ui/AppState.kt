package com.example.githubclient.ui

sealed class AppState {
    data class Success<T>(val data: T) : AppState()
    data class Error(val error: String) : AppState()
    object Loading : AppState()
}
