package com.example.githubclient.ui.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubclient.domain.RepositoryUseCase

class UserListViewModelFactory(
    private val repositoryUseCase: RepositoryUseCase,
    private val remoteRepositoryUseCase: RepositoryUseCase,
    private val id: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserListViewModel(
            repositoryUseCase,
            remoteRepositoryUseCase,
            id
        ) as T
    }
}