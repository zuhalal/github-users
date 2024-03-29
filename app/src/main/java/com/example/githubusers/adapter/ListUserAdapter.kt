package com.example.githubusers.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.githubusers.data.remote.models.UserResponseItem
import com.example.githubusers.databinding.ItemRowUserBinding
import com.example.githubusers.extensions.loadImage
import com.example.githubusers.helper.UserDiffCallback

class ListUserAdapter() :
    RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {
    private val listUser = ArrayList<UserResponseItem>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setListUser(listUser: List<UserResponseItem>) {
        val diffCallback = UserDiffCallback(this.listUser, listUser)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listUser.clear()
        this.listUser.addAll(listUser)
        diffResult.dispatchUpdatesTo(this)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UserResponseItem, index: Int)
    }

    class ListViewHolder(itemView: ItemRowUserBinding) : RecyclerView.ViewHolder(itemView.root) {
        var tvUsername: TextView = itemView.tvUsername
        var tvName: TextView = itemView.tvName
        var imgPhoto: ImageView = itemView.imgItemPhoto
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]

        holder.imgPhoto.loadImage(user.avatarUrl)

        holder.tvUsername.text = user.login
        holder.tvName.text = user.htmlUrl

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(
                listUser[holder.adapterPosition],
                holder.adapterPosition
            )
        }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }
}