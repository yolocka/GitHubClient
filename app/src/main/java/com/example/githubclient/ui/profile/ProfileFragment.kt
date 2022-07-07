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
import com.example.githubclient.domain.entities.RepoEntity
import com.example.githubclient.domain.entities.UserEntity
import com.example.githubclient.databinding.FragmentProfileBinding
import com.example.githubclient.di.AppDependenciesModule
import com.example.githubclient.domain.RepositoryUseCase
import com.example.githubclient.ui.AppState
import com.example.githubclient.utils.ViewModelStore
import java.util.*
import javax.inject.Inject
import javax.inject.Named


class ProfileFragment : Fragment() {

    private val binding by viewBinding(FragmentProfileBinding::class.java)
    private val repoListAdapter = RepoListAdapter()
    private lateinit var viewModel: ProfileViewModel
    @Inject
    lateinit var viewModelStore: ViewModelStore
    @Inject
    @Named(AppDependenciesModule.LOCAL_REPOSITORY)
    lateinit var localRepo: RepositoryUseCase
    @Inject
    @Named(AppDependenciesModule.REMOTE_REPOSITORY_FOR_REPOS)
    lateinit var remoteReposRepo: RepositoryUseCase

    companion object{
        private const val USER_ID_ARGS_KEY = "USER_ID_ARGS_KEY"
        const val PROFILE_VIEW_MODEL_ID: String = "profile_view_model_id"
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

        app.appDependenciesComponent.inject(this)

        if (savedInstanceState != null) {
            val id = savedInstanceState.getString(PROFILE_VIEW_MODEL_ID)!!
            viewModel = viewModelStore.getViewModel(id) as ProfileViewModel
        } else {
            val id = UUID.randomUUID().toString()
            viewModel = ViewModelProvider(
                this,
                ProfileViewModelFactory (localRepo, remoteReposRepo, id)
            ).get(ProfileViewModel::class.java)
            viewModelStore.saveViewModel(viewModel)
        }

        binding.repoListRecyclerView.apply{
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
            adapter = repoListAdapter
        }

        viewModel.getData().observe(requireActivity()) { state
            ->
            render(state)
        }
        val userId = getUserIdFromArguments()
        userId.let {
            viewModel.getUserData(it)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(PROFILE_VIEW_MODEL_ID, viewModel.id)
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