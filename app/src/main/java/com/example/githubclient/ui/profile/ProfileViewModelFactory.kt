package com.example.githubclient.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubclient.domain.UsersUseCase

class ProfileViewModelFactory (
    private val usersUseCase: UsersUseCase
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T: ViewModel> create(modelClass:Class<T>): T {
        return ProfileViewModel(usersUseCase) as T
    }
}