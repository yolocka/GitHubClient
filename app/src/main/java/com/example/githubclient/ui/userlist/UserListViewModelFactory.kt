package com.example.githubclient.ui.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubclient.domain.RepositoryUseCase

class UserListViewModelFactory (
    private val repositoryUseCase: RepositoryUseCase,
    val id: String
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T: ViewModel> create(modelClass:Class<T>): T {
        return UserListViewModel(repositoryUseCase, id) as T
    }
}