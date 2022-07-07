package com.example.githubclient.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubclient.domain.RepositoryUseCase

class ProfileViewModelFactory (
    private val repositoryUseCase: RepositoryUseCase,
    private val remoteRepositoryUseCase: RepositoryUseCase,
    private val id: String
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProfileViewModel(
                repositoryUseCase,
                remoteRepositoryUseCase,
                id
            ) as T
        }
}