package com.example.githubclient


import com.example.githubclient.data.web.GitHubUsersApi
import com.example.githubclient.data.web.RemoteRepositoryImpl
import com.example.githubclient.domain.RepositoryUseCase
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import org.junit.After
import org.junit.Assert.*
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RemoteRepositoryImplTest {
    var retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    var remoteUsersRepo: RepositoryUseCase = RemoteRepositoryImpl(retrofit.create(GitHubUsersApi::class.java))

    @Test
    fun getUsersFromRemoteSource_ResponseIsNotEmpty() {
        compositeDisposable.add(
            remoteUsersRepo
                .getUsersFromRemoteSource()
                .subscribeBy {
                    assertFalse(it.isEmpty())
                }
        )
    }

    @Test
    fun getUsersFromRemoteSource_ResponseIsNotNull() {
        compositeDisposable.add(
            remoteUsersRepo
                .getUsersFromRemoteSource()
                .subscribeBy {
                    assertNotNull(it)
                }
        )
    }

    @After
    fun cleaning() {
        compositeDisposable.clear()
    }
}