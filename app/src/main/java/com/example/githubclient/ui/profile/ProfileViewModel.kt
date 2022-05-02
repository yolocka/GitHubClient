package com.example.githubclient.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubclient.domain.RepositoryUseCase
import com.example.githubclient.domain.entities.RepoEntity
import com.example.githubclient.ui.AppState
import com.example.githubclient.ui.MainActivity
import com.example.githubclient.utils.ViewModelStore
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class ProfileViewModel(
    private val repositoryUseCase: RepositoryUseCase, override val id: String
) : ViewModel(), ProfileContract.ViewModel, ViewModelStore.BaseViewModel {

    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun getData(): LiveData<AppState> = liveDataToObserve

    override fun getUserData(id: Long) {
        liveDataToObserve.value = AppState.Loading
        repositoryUseCase.getOneUser(id) { result ->
            liveDataToObserve.postValue(AppState.Success(result))
            //getRepoList(id)
        }
    }

    override fun getRepoList(id: Long) {
        liveDataToObserve.value = AppState.Loading
        compositeDisposable.add(
            repositoryUseCase
                .getRepositories(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy { result ->
                    if (result.isNotEmpty()) {
                        liveDataToObserve.postValue(AppState.AdditionalDataSuccess(result))
                    } else {
                        liveDataToObserve.value = AppState.Error(MainActivity.ERR_EMPTY_DATA)
                    }
                }
        )
    }

    override fun observeUsersRepo(login: String) {
        liveDataToObserve.value = AppState.Loading
        compositeDisposable.add(
            repositoryUseCase
                .observeUsersRepos(login)
                .subscribeBy {
                    if (it.isNotEmpty()) {
                        liveDataToObserve.postValue(AppState.AdditionalDataSuccess(
                            it.map { repoDto ->
                                RepoEntity (
                                    id = repoDto.id,
                                    name = repoDto.name,
                                    userId = repoDto.userId
                                )
                            }
                        ))
                    } else {
                        liveDataToObserve.value = AppState.Error(MainActivity.ERR_EMPTY_DATA)
                    }
                }
        )
    }

    override fun addRepoToLocalRepo(repo: RepoEntity) {
        TODO("Not yet implemented")
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}