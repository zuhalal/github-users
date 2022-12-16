package com.example.githubusers.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubusers.R
import com.example.githubusers.models.UserResponseItem

class ListUserFragmentAdapter(private val context: Context, private val listUser: List<UserResponseItem>):
    RecyclerView.Adapter<ListUserFragmentAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: UserResponseItem, index: Int)
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvUsername: TextView = itemView.findViewById(R.id.tv_username)
        var tvName: TextView = itemView.findViewById(R.id.tv_name)
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent,false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]

        Glide.with(context)
            .load(user.avatarUrl)
            .apply(RequestOptions().override(55,55))
            .into(holder.imgPhoto)
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