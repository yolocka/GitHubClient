package com.example.githubclient.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.githubclient.R
import com.example.githubclient.domain.entities.RepoEntity

class RepoListAdapter() : RecyclerView.Adapter<RepoListAdapter.MainViewHolder>() {

    private var repositories: List<RepoEntity> = listOf()

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(repo: RepoEntity) {
            itemView.apply {
                findViewById<TextView>(R.id.repo_item_text_view).text = repo.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.repo_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(repositories[position])
    }

    override fun getItemCount(): Int = repositories.size

    fun setRepositories(data: List<RepoEntity>) {
        val mainDiffUtilCallback = RepoDiffUtilCallback(repositories, data)
        val productDiffResult = DiffUtil.calculateDiff(mainDiffUtilCallback)
        repositories = data
        productDiffResult.dispatchUpdatesTo(this)
    }
}