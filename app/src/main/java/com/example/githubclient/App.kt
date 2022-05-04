package com.example.githubclient

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import com.example.githubclient.di.AppDependenciesComponent
import com.example.githubclient.di.AppDependenciesModule
import com.example.githubclient.di.DaggerAppDependenciesComponent


class App : Application() {
    lateinit var appDependenciesComponent: AppDependenciesComponent

    override fun onCreate() {
        super.onCreate()
        appDependenciesComponent = DaggerAppDependenciesComponent
            .builder()
            .appDependenciesModule(AppDependenciesModule(this))
            .build()
    }
}

val Context.app: App
    get() = applicationContext as App

val Fragment.app: App
    get() = requireActivity().app