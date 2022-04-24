package com.example.githubclient.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubclient.domain.UsersUseCase
import com.example.githubclient.data.entities.RepoDto
import com.example.githubclient.ui.AppState
import com.example.githubclient.ui.MainActivity
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy

class ProfileViewModel(
    private val usersUseCase: UsersUseCase
) : ViewModel(), ProfileContract.ViewModel {

    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun getData(): LiveData<AppState> = liveDataToObserve

    override fun getUserData(id: Long) {
        liveDataToObserve.value = AppState.Loading
        usersUseCase.getOneUser(id) { result ->
            liveDataToObserve.postValue(AppState.Success(result))
            //getRepoList(id)
        }
    }

    override fun getRepoList(id: Long) {
        usersUseCase.getRepositories(id) { result ->
            if (result.isNotEmpty()) {
                liveDataToObserve.postValue(AppState.AdditionalDataSuccess(result))
            } else {
                liveDataToObserve.value = AppState.Error(MainActivity.ERR_EMPTY_DATA)
            } }
    }

    override fun observeUsersRepo(login: String) {
        liveDataToObserve.value = AppState.Loading
        compositeDisposable.add(
            usersUseCase
                .observeUsersRepos(login)
                .subscribeBy {
                    if (it.isNotEmpty()) {
                        liveDataToObserve.postValue(AppState.AdditionalDataSuccess(it))
                    } else {
                        liveDataToObserve.value = AppState.Error(MainActivity.ERR_EMPTY_DATA)
                    }
                }
        )
    }

    override fun addRepoToLocalRepo(repo: RepoDto) {
        TODO("Not yet implemented")
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}