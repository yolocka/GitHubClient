package com.example.githubclient.di

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import com.example.githubclient.data.db.LocalRepositoryImpl
import com.example.githubclient.data.db.UsersDAO
import com.example.githubclient.data.db.UsersDataBase
import com.example.githubclient.data.web.GitHubReposApi
import com.example.githubclient.data.web.GitHubUsersApi
import com.example.githubclient.data.web.RemoteRepositoryImpl
import com.example.githubclient.domain.GitHubApi
import com.example.githubclient.domain.RepositoryUseCase
import com.example.githubclient.utils.ViewModelStore
import dagger.Module
import dagger.Provides
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import javax.inject.Named
import javax.inject.Singleton



/*val appModule = module {




    single(named(API_URL_KEY)) { API_URL_VALUE }


    single(named(VIEW_MODEL_ID)){ UUID.randomUUID().toString() }

    viewModel(named(USER_LIST_VIEW_MODEL)) {
        UserListViewModel(
            get(named(LOCAL_REPOSITORY)),
            get(named(REMOTE_REPOSITORY_FOR_USERS)),
            get(named(VIEW_MODEL_ID))
        )
    }
    viewModel(named(PROFILE_VIEW_MODEL)) {
        ProfileViewModel(
            get(named(LOCAL_REPOSITORY)),
            get(named(REMOTE_REPOSITORY_FOR_REPOS)),
            get(named(VIEW_MODEL_ID))
        )
    }
}*/

@Module
class AppDependenciesModule(private val application: Application)  {

    companion object {
        const val REMOTE_REPOSITORY_FOR_REPOS: String = "repos_repository"
        const val REMOTE_REPOSITORY_FOR_USERS: String = "users_repository"
        const val LOCAL_REPOSITORY: String = "local_repo"
        const val API_FOR_REPOS: String = "repos_api"
        const val API_FOR_USERS: String = "users_api"
    }

    @Provides
    @Singleton
    fun providesApplicationContext(): Context = application

    @Provides
    @Singleton
    fun providesHandler(): Handler = Handler(Looper.getMainLooper())

    @Provides
    @Singleton
    fun providesDao(context: Context): UsersDAO = UsersDataBase.getInstance(context)

    @Singleton
    @Provides
    @Named(LOCAL_REPOSITORY)
    fun provideLocalRepo(usersDAO: UsersDAO, handler: Handler): RepositoryUseCase {
        return LocalRepositoryImpl(usersDAO, handler)
    }

    @Singleton
    @Provides
    fun provideBaseUrl() : String {
        return "https://api.github.com/"
    }

    @Provides
    fun provideConverterFactory() : Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(baseUrl: String, converterFactory: Converter.Factory) : Retrofit {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(converterFactory)
                .build()
    }

    @Singleton
    @Provides
    @Named(API_FOR_USERS)
    fun provideUsersApi(retrofit: Retrofit) : GitHubApi {
        return retrofit.create(GitHubUsersApi::class.java)
    }

    @Singleton
    @Provides
    @Named(API_FOR_REPOS)
    fun provideReposApi(retrofit: Retrofit) : GitHubApi {
        return retrofit.create(GitHubReposApi::class.java)
    }

    @Singleton
    @Provides
    @Named(REMOTE_REPOSITORY_FOR_REPOS)
    fun provideRemoteRepositoryForRepos(@Named(API_FOR_REPOS) api: GitHubApi) : RepositoryUseCase {
        return RemoteRepositoryImpl(api)
    }

    @Singleton
    @Provides
    @Named(REMOTE_REPOSITORY_FOR_USERS)
    fun provideRemoteRepositoryForUsers(@Named(API_FOR_USERS) api: GitHubApi) : RepositoryUseCase {
        return RemoteRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun provideViewModelStore() : ViewModelStore = ViewModelStore()
}