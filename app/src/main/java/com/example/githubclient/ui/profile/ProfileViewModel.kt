package com.example.githubclient.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubclient.domain.UsersUseCase
import com.example.githubclient.ui.AppState

class ProfileViewModel(
    private val usersUseCase: UsersUseCase
) : ViewModel(), ProfileContract.ViewModel {

    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()

    fun getData(): LiveData<AppState> = liveDataToObserve

    override fun getUserData(id: Int) {
        liveDataToObserve.value = AppState.Loading
        usersUseCase.getOneUser(id) { result ->
            liveDataToObserve.postValue(AppState.Success(result))
            getRepoList(id)
        }
    }

    override fun getRepoList(id: Int) {
        usersUseCase.getRepositories(id) { result ->
            if (result.isNotEmpty()) {
                liveDataToObserve.postValue(AppState.AdditionalDataSuccess(result))
            } else {
                liveDataToObserve.value = AppState.Error(ProfileActivity.ERR_EMPTY_DATA)
            } }
    }
}