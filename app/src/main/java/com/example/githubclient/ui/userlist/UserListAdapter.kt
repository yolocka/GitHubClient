package com.example.githubclient.ui.userlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.githubclient.R
import com.example.githubclient.data.entities.UserEntity

class UserListAdapter(
    private val itemClickCallback: (UserEntity) -> Unit
) : RecyclerView.Adapter<UserListAdapter.MainViewHolder>(){

    private var users: List<UserEntity> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(users[position], itemClickCallback)
    }

    override fun getItemCount(): Int = users.size

    fun setUsers(data: List<UserEntity>) {
        val mainDiffUtilCallback = UserDiffUtilCallback(users, data)
        val productDiffResult = DiffUtil.calculateDiff(mainDiffUtilCallback)
        users = data
        productDiffResult.dispatchUpdatesTo(this)
    }


    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(user: UserEntity, listener: (UserEntity) -> Unit) {
            itemView.apply {
                findViewById<TextView>(R.id.user_item_text_view).text = user.login
                setOnClickListener{
                    //listener?.onClick(user)
                    listener.invoke(user)
                }
            }
        }
    }
}