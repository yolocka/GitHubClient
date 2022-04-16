package com.example.githubclient.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubclient.domain.UsersUseCase

class UserListViewModelFactory (
    private val usersUseCase: UsersUseCase
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T: ViewModel> create(modelClass:Class<T>): T {
        return UserListViewModel(usersUseCase) as T
    }
}