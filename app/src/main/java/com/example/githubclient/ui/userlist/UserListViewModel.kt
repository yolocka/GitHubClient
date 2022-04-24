package com.example.githubclient.ui.userlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubclient.domain.UsersUseCase
import com.example.githubclient.domain.entities.RepoDTO
import com.example.githubclient.domain.entities.UserDTO
import com.example.githubclient.ui.AppState

class UserListViewModel(
    private val usersUseCase: UsersUseCase
) : ViewModel(), UserListContract.ViewModel {

    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()

    fun getData(): LiveData<AppState> = liveDataToObserve

    override fun getUsers() {
        liveDataToObserve.value = AppState.Loading
        usersUseCase.getUsers { result ->
            if (result.isNotEmpty()) {
                liveDataToObserve.postValue(AppState.Success(result))
            } else {
                liveDataToObserve.value = AppState.Error(MainActivity.ERR_EMPTY_DATA)
            } }
    }

    override fun updateData(userProfile: UserDTO) {
        liveDataToObserve.value = AppState.Loading
        usersUseCase.addUser(userProfile) { result ->
            if (!result) {
                liveDataToObserve.value = AppState.Error(MainActivity.ERR_UPDATE_DATA)
            }
        }
    }

    override fun updateRepo(repository: RepoDTO) {
        liveDataToObserve.value = AppState.Loading
        usersUseCase.addRepo(repository){ result ->
            if (!result) {
                liveDataToObserve.value = AppState.Error(MainActivity.ERR_UPDATE_DATA)
            }
        }
    }
}