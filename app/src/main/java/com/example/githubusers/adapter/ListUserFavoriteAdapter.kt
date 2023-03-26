package com.example.githubusers.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.githubusers.data.local.entity.FavoriteUserEntity
import com.example.githubusers.databinding.ItemRowUserBinding
import com.example.githubusers.extensions.loadImage

// main differences is we're using ListAdapter
class FavoriteUserAdapter(private val onFavoriteClick: (FavoriteUserEntity) -> Unit) : ListAdapter<FavoriteUserEntity, FavoriteUserAdapter.FavoriteUserViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: FavoriteUserEntity, index: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteUserViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteUserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(
                user,
                holder.adapterPosition
            )
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: FavoriteUserAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class FavoriteUserViewHolder(val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(news: FavoriteUserEntity) {
            binding.tvUsername.text = news.username
            binding.tvName.text = news.htmlUrl
            binding.imgItemPhoto.loadImage(news.avatarUrl)
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<FavoriteUserEntity> =
            object : DiffUtil.ItemCallback<FavoriteUserEntity>() {
                override fun areItemsTheSame(oldUser: FavoriteUserEntity, newUser: FavoriteUserEntity): Boolean {
                    return oldUser.htmlUrl == newUser.htmlUrl
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldUser: FavoriteUserEntity, newUser: FavoriteUserEntity): Boolean {
                    return oldUser == newUser
                }
            }
    }
}