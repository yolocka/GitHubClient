package com.example.githubclient.ui.userlist

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubclient.app
import com.example.githubclient.databinding.ActivityMainBinding
import com.example.githubclient.domain.entities.UserDTO
import com.example.githubclient.ui.AppState
import com.example.githubclient.ui.profile.ProfileActivity

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val userListAdapter = UserListAdapter()
    private lateinit var viewModel: UserListViewModel
    private val sharedPref: SharedPreferences by lazy {
        getSharedPreferences(SHAR_PREF_NAME, Context.MODE_PRIVATE)
    }
    private val editor: SharedPreferences.Editor by lazy {
        sharedPref.edit()
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
        binding.usersListRecyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        binding.usersListRecyclerView.adapter = userListAdapter
        setOnClickListToAdapter(userListAdapter)
        viewModel = ViewModelProvider(
            this,
            UserListViewModelFactory (app.usersUseCase)
        ).get(UserListViewModel::class.java)
        viewModel.getData().observe(this) { state
            ->
            render(state)
        }
        if (sharedPref.getBoolean(IS_REPO_EMPTY, true)) {
            editor.let {
                editor.putBoolean(IS_REPO_EMPTY, false)
                editor.apply()
            }
            viewModel.getUsersFromRemoteSource(true)
        } else {
            viewModel.getUsersFromRemoteSource(false)
            viewModel.getUsers()
        }
    }

    private fun render(state: AppState?) {
        when (state) {
            is AppState.Success<*> -> {
                //hideProgress()
                val users: List<UserDTO> = state.data as List<UserDTO>
                userListAdapter.setUsers(users)
            }
            is AppState.Error -> {
                /*hideProgress()
                setError(state.error)*/
            }
            is AppState.Loading -> {
                //showProgress()
            }
            else -> {}
        }
    }

    private fun setOnClickListToAdapter(adapter: UserListAdapter) {
        adapter.listener = UserListAdapter.OnItemClick { user ->
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("USER_ID", user.id)
            startActivity(intent)
        }
    }

}