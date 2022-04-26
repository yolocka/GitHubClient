package com.example.githubclient.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubclient.domain.UsersUseCase

class ProfileViewModelFactory (
    private val usersUseCase: UsersUseCase,
    val id: String
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T: ViewModel> create(modelClass:Class<T>): T {
        return ProfileViewModel(usersUseCase, id) as T
    }
}