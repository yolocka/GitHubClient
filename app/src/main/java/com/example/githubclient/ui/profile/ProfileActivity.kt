package com.example.githubclient.ui.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.githubclient.app
import com.example.githubclient.databinding.ActivityProfileBinding
import com.example.githubclient.domain.entities.UserProfile
import com.example.githubclient.ui.AppState

class ProfileActivity : AppCompatActivity() {
    private val binding: ActivityProfileBinding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        var userId = intent.getIntExtra("USER_ID", 0)
        viewModel = ViewModelProvider(
            this,
            ProfileViewModelFactory (app.usersUseCase)
        ).get(ProfileViewModel::class.java)
        viewModel.getData().observe(this) { state
            ->
            render(state)
        }
        userId?.let { viewModel.getUserData(it) }
    }

    private fun render(state: AppState?) {
        when (state) {
            is AppState.Success<*> -> {
                //hideProgress()
                val user: UserProfile = state.data as UserProfile
                binding.userNameTextView.text = user.name
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