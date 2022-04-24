package com.example.githubclient.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.example.githubclient.R
import com.example.githubclient.app
import com.example.githubclient.data.entities.RepoEntity
import com.example.githubclient.data.entities.UserEntity
import com.example.githubclient.databinding.FragmentProfileBinding
import com.example.githubclient.ui.AppState


class ProfileFragment : Fragment() {

    private val binding by viewBinding(FragmentProfileBinding::class.java)
    private val repoListAdapter = RepoListAdapter()
    private lateinit var viewModel: ProfileViewModel

    companion object{
        private const val USER_ID_ARGS_KEY = "USER_ID_ARGS_KEY"
        fun newInstance(userId: Long) = ProfileFragment().apply {
            arguments = Bundle()
            arguments?.putLong(USER_ID_ARGS_KEY, userId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.repoListRecyclerView.apply{
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
            adapter = repoListAdapter
        }
        viewModel = ViewModelProvider(
            this,
            ProfileViewModelFactory (app.usersUseCase)
        ).get(ProfileViewModel::class.java)
        viewModel.getData().observe(requireActivity()) { state
            ->
            render(state)
        }
        val userId = getUserIdFromArguments()
        userId.let {
            viewModel.getUserData(it)
        }
    }

    private fun render(state: AppState?) {
        when (state) {
            is AppState.Success<*> -> {
                //hideProgress()
                val user: UserEntity = state.data as UserEntity
                binding.userNameTextView.text = user.login
                binding.userPhotoImageView.load(user.avatar_url)
                viewModel.observeUsersRepo(user.login)
            }
            is AppState.AdditionalDataSuccess<*> -> {
                val repositories: List<RepoEntity> = state.data as List<RepoEntity>
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

    private fun getUserIdFromArguments(): Long {
        return arguments?.getLong(USER_ID_ARGS_KEY)
            ?: throw IllegalStateException("В аргументы не добавлен id пользователя")
    }
}