package com.example.githubclient.di

import com.example.githubclient.ui.profile.ProfileFragment
import com.example.githubclient.ui.userlist.UsersListFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AppDependenciesModule::class
    ]
)
interface AppDependenciesComponent {

    fun inject(usersListFragment: UsersListFragment)
    fun inject(profileFragment: ProfileFragment)
}