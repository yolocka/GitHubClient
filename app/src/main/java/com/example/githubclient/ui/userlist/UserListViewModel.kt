package com.example.githubclient.ui.userlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubclient.ui.MainActivity
import com.example.githubclient.domain.UsersUseCase
import com.example.githubclient.data.entities.UserEntity
import com.example.githubclient.ui.AppState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy

class UserListViewModel(
    private val usersUseCase: UsersUseCase
) : ViewModel(), UserListContract.ViewModel {

    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

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

    override fun updateData(userProfile: UserEntity) {
        liveDataToObserve.value = AppState.Loading
        usersUseCase.addUser(userProfile) { result ->
            if (!result) {
                liveDataToObserve.value = AppState.Error(MainActivity.ERR_UPDATE_DATA)
            }
        }
    }

    override fun getUsersFromRemoteSource(isItFirstTime: Boolean) {
        liveDataToObserve.value = AppState.Loading
        compositeDisposable.add(
            usersUseCase
                .getUsersFromRemoteSource()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy {
                    it.forEach { user ->
                        updateData(
                            UserEntity(
                                id = user.id,
                                login = user.login,
                                avatar_url = user.avatar_url
                            )
                        )
                    }
                    if (isItFirstTime) {
                        liveDataToObserve.postValue(AppState.Success(it))
                    }
                }
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}