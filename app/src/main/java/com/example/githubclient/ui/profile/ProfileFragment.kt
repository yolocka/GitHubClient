package com.example.githubclient.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.example.githubclient.R
import com.example.githubclient.domain.entities.RepoEntity
import com.example.githubclient.domain.entities.UserEntity
import com.example.githubclient.databinding.FragmentProfileBinding
import com.example.githubclient.ui.AppState
import com.example.githubclient.utils.ViewModelStore
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named


class ProfileFragment : Fragment() {

    private val binding by viewBinding(FragmentProfileBinding::class.java)
    private val repoListAdapter = RepoListAdapter()
    private lateinit var viewModel: ProfileViewModel
    private val viewModelStore: ViewModelStore by inject()

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

        if (savedInstanceState != null) {
            val id = savedInstanceState.getString(PROFILE_VIEW_MODEL_ID)!!
            viewModel = viewModelStore.getViewModel(id) as ProfileViewModel
        } else {
            viewModel = get(named("profile_view_model"))
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