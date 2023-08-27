package com.dicoding.githubuser.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubuser.data.remote.response.UserFollowerFollowing
import com.dicoding.githubuser.databinding.ItemRowUserBinding

class UserFollowerFollowingAdapter :
    ListAdapter<UserFollowerFollowing, UserFollowerFollowingAdapter.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder(private val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserFollowerFollowing) {
            fun ImageView.loadImage(url: String) {
                Glide.with(this.context)
                    .load(url)
                    .centerCrop()
                    .into(this)
            }

            binding.apply {
                tvItemUsername.text = user.login
                ivItemUserPicture.loadImage(
                    url = user.avatarUrl
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val user = getItem(position)
        holder.bind(user)
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserFollowerFollowing>() {
            override fun areItemsTheSame(
                oldItem: UserFollowerFollowing,
                newItem: UserFollowerFollowing
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: UserFollowerFollowing,
                newItem: UserFollowerFollowing
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
