package com.example.githubclient.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.githubclient.R
import com.example.githubclient.domain.entities.UserProfile

class UserListAdapter() : RecyclerView.Adapter<UserListAdapter.MainViewHolder>(){

    private var users: List<UserProfile> = listOf()
    var listener: OnItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size

    fun setUsers(data: List<UserProfile>) {
        val mainDiffUtilCallback = UserDiffUtilCallback(users, data)
        val productDiffResult = DiffUtil.calculateDiff(mainDiffUtilCallback)
        users = data
        productDiffResult.dispatchUpdatesTo(this)
    }


    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(user: UserProfile) {
            itemView.apply {
                findViewById<TextView>(R.id.user_item_text_view).text = user.name
            }
        }
    }

    fun interface OnItemClick {
        fun onClick(user: UserProfile)
    }
}