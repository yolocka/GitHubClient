package com.example.githubclient.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubclient.domain.UsersUseCase

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
}