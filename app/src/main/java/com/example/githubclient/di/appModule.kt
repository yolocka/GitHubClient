package com.example.githubclient.di

import android.os.Handler
import android.os.Looper
import com.example.githubclient.data.RepositoryUseCaseImpl
import com.example.githubclient.data.db.LocalRepositoryImpl
import com.example.githubclient.data.db.UsersDataBase
import com.example.githubclient.data.web.GitHubReposApi
import com.example.githubclient.data.web.GitHubUsersApi
import com.example.githubclient.data.web.RemoteRepositoryImpl
import com.example.githubclient.domain.GitHubApi
import com.example.githubclient.domain.LocalRepository
import com.example.githubclient.domain.RemoteRepository
import com.example.githubclient.domain.RepositoryUseCase
import com.example.githubclient.ui.profile.ProfileViewModel
import com.example.githubclient.ui.userlist.UserListViewModel
import com.example.githubclient.utils.ViewModelStore
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

const val USE_CASE_FOR_REPOS: String = "repos_use_case"
const val USE_CASE_FOR_USERS: String = "users_use_case"
const val REPOSITORY_FOR_REPOS: String = "repos_repository"
const val REPOSITORY_FOR_USERS: String = "users_repository"
const val API_FOR_REPOS: String = "repos_api"
const val API_FOR_USERS: String = "users_api"
const val API_URL_KEY: String = "api_url"
const val API_URL_VALUE: String = "https://api.github.com/"
const val VIEW_MODEL_ID: String = "view_model_id"
const val USER_LIST_VIEW_MODEL: String = "users_list_view_model"
const val PROFILE_VIEW_MODEL: String = "profile_view_model"


val appModule = module {

    single<LocalRepository> { LocalRepositoryImpl(UsersDataBase.getInstance(androidContext())) }

    single<RemoteRepository>(named(REPOSITORY_FOR_REPOS)) {
        RemoteRepositoryImpl(get(named(API_FOR_REPOS)))
    }
    single<RemoteRepository>(named(REPOSITORY_FOR_USERS)) {
        RemoteRepositoryImpl(get(named(API_FOR_USERS)))
    }
    single <GitHubApi>(named(API_FOR_REPOS)) { get<Retrofit>().create(GitHubReposApi::class.java) }
    single <GitHubApi>(named(API_FOR_USERS)) { get<Retrofit>().create(GitHubUsersApi::class.java) }
    single<Retrofit> {
        Retrofit.Builder()
        .baseUrl(get<String>(named(API_URL_KEY)))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(get())
        .build()
    }
    single(named(API_URL_KEY)) { API_URL_VALUE }

    single<RepositoryUseCase>(named(USE_CASE_FOR_REPOS)) {
        RepositoryUseCaseImpl(
            get(),
            Handler(Looper.getMainLooper()),
            get(named(REPOSITORY_FOR_REPOS)))
    }
    single<RepositoryUseCase>(named(USE_CASE_FOR_USERS)) {
        RepositoryUseCaseImpl(
            get(),
            Handler(Looper.getMainLooper()),
            get(named(REPOSITORY_FOR_USERS)))
    }


    factory<Converter.Factory> { GsonConverterFactory.create() }

    single{ ViewModelStore() }
    single(named(VIEW_MODEL_ID)){ UUID.randomUUID().toString() }

    viewModel(named(USER_LIST_VIEW_MODEL)) { UserListViewModel(get(named(USE_CASE_FOR_USERS)), get(named(VIEW_MODEL_ID))) }
    viewModel(named(PROFILE_VIEW_MODEL)) { ProfileViewModel(get(named(USE_CASE_FOR_REPOS)), get(named(VIEW_MODEL_ID))) }
}