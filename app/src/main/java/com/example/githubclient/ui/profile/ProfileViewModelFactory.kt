package com.example.githubclient.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubclient.domain.RepositoryUseCase

class ProfileViewModelFactory (
    private val repositoryUseCase: RepositoryUseCase,
    val id: String
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T: ViewModel> create(modelClass:Class<T>): T {
        return ProfileViewModel(repositoryUseCase, id) as T
    }
}