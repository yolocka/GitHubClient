package com.example.githubclient.ui.userlist

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.githubclient.R
import com.example.githubclient.app
import com.example.githubclient.ui.MainActivity
import com.example.githubclient.domain.entities.UserEntity
import com.example.githubclient.databinding.FragmentUsersListBinding
import com.example.githubclient.di.AppDependenciesModule
import com.example.githubclient.domain.RepositoryUseCase
import com.example.githubclient.ui.AppState
import com.example.githubclient.utils.ViewModelStore
import java.util.*
import javax.inject.Inject
import javax.inject.Named

class UsersListFragment : Fragment() {

    private val binding by viewBinding(FragmentUsersListBinding::class.java)
    private val userListAdapter = UserListAdapter{controller.openUserProfileScreen(it)}
    private lateinit var viewModel: UserListViewModel
    private val controller by lazy { activity as Controller }
    private val sharedPref: SharedPreferences by lazy {
        requireActivity().getSharedPreferences(MainActivity.SHAR_PREF_NAME, Context.MODE_PRIVATE)
    }
    private val editor: SharedPreferences.Editor by lazy {
        sharedPref.edit()
    }
    @Inject
    lateinit var viewModelStore: ViewModelStore
    @Inject
    @Named(AppDependenciesModule.LOCAL_REPOSITORY)
    lateinit var localRepo: RepositoryUseCase
    @Inject
    @Named(AppDependenciesModule.REMOTE_REPOSITORY_FOR_USERS)
    lateinit var remoteUsersRepo: RepositoryUseCase

    companion object {
        const val USERS_LIST_VIEW_MODEL_ID: String = "users_list_view_model_id"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_users_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        app.appDependenciesComponent.inject(this)

        if (savedInstanceState != null) {
            val id = savedInstanceState.getString(USERS_LIST_VIEW_MODEL_ID)!!
            viewModel = viewModelStore.getViewModel(id) as UserListViewModel
        } else {
            val id = UUID.randomUUID().toString()
            viewModel = ViewModelProvider(
                this,
                UserListViewModelFactory (localRepo, remoteUsersRepo, id)
            ).get(UserListViewModel::class.java)
            viewModelStore.saveViewModel(viewModel)
        }

        binding.usersListRecyclerView.apply {
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
            layoutManager = LinearLayoutManager(requireContext())
            adapter = userListAdapter
        }

        viewModel.getData().observe(requireActivity()) { state
            ->
            render(state)
        }

        if (sharedPref.getBoolean(MainActivity.IS_REPO_EMPTY, true)) {
            editor.let {
                it.apply {
                    putBoolean(MainActivity.IS_REPO_EMPTY, false)
                    apply()
                }
            }
            viewModel.getUsersFromRemoteSource(true)
        } else {
            viewModel.apply {
                getUsersFromRemoteSource(false)
                getUsers()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(USERS_LIST_VIEW_MODEL_ID, viewModel.id)
    }

    private fun render(state: AppState?) {
        when (state) {
            is AppState.Success<*> -> {
                //hideProgress()
                val users: List<UserEntity> = state.data as List<UserEntity>
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

    interface Controller {
        fun openUserProfileScreen(user: UserEntity)
    }
}