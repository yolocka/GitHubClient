package com.example.githubclient.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubclient.app
import com.example.githubclient.databinding.ActivityMainBinding
import com.example.githubclient.domain.entities.UserProfile

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val userListAdapter = UserListAdapter()
    private lateinit var viewModel: UserListViewModel

    companion object {
        const val ERR_EMPTY_DATA: String = "Не удалось загрузить данные"
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
        viewModel.getUsers()
    }

    private fun render(state: AppState?) {
        when (state) {
            is AppState.Success<*> -> {
                //hideProgress()
                val users: List<UserProfile> = state.data as List<UserProfile>
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

    }
}