package com.example.githubclient.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.githubclient.databinding.ActivityMainBinding
import com.example.githubclient.domain.RepositoryUseCase
import com.example.githubclient.domain.entities.UserEntity
import com.example.githubclient.ui.profile.ProfileFragment
import com.example.githubclient.ui.userlist.UsersListFragment

class MainActivity : AppCompatActivity(), UsersListFragment.Controller {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    companion object {
        const val ERR_EMPTY_DATA: String = "Не удалось загрузить данные"
        const val ERR_UPDATE_DATA: String = "Не удалось обновить данные"
        const val SHAR_PREF_NAME: String = "MY_SHAR_PREF"
        const val IS_REPO_EMPTY: String = "IS_REPO_EMPTY"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            .add(binding.root.id, UsersListFragment())
            .commit()
    }

    override fun openUserProfileScreen(user: UserEntity) {

        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(
                binding.root.id,
                ProfileFragment.newInstance(user.id)
            )
            .commit()
    }

}