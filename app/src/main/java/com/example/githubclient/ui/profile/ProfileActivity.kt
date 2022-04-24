package com.example.githubclient.ui.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.githubclient.app
import com.example.githubclient.databinding.ActivityProfileBinding
import com.example.githubclient.domain.entities.RepoDTO
import com.example.githubclient.domain.entities.UserDTO
import com.example.githubclient.ui.AppState

class ProfileActivity : AppCompatActivity() {
    private val binding: ActivityProfileBinding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }
    private val repoListAdapter = RepoListAdapter()
    private lateinit var viewModel: ProfileViewModel

    companion object {
        const val ERR_EMPTY_DATA: String = "Не удалось загрузить данные"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.repoListRecyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        binding.repoListRecyclerView.adapter = repoListAdapter
        var userId = intent.getLongExtra("USER_ID", 0)
        viewModel = ViewModelProvider(
            this,
            ProfileViewModelFactory (app.usersUseCase)
        ).get(ProfileViewModel::class.java)
        viewModel.getData().observe(this) { state
            ->
            render(state)
        }
        userId.let {
            viewModel.getUserData(it)
        }
    }

    private fun render(state: AppState?) {
        when (state) {
            is AppState.Success<*> -> {
                //hideProgress()
                val user: UserDTO = state.data as UserDTO
                binding.userNameTextView.text = user.login
                binding.userPhotoImageView.load(user.avatar_url)
                viewModel.observeUsersRepo(user.login)
            }
            is AppState.AdditionalDataSuccess<*> -> {
                val repositories: List<RepoDTO> = state.data as List<RepoDTO>
                repoListAdapter.setRepositories(repositories)
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
}