package com.example.sfoide.ui.userlist.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sfoide.entities.UserData

class UserListRecyclerViewAdapter(
    private val onItemClick: (UserData.Result) -> Unit,
) : ListAdapter<UserData.Result, RecyclerView.ViewHolder>(UserDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return UserViewHolder(parent, onItemClick)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as UserViewHolder).bind(getItem(position))
    }

    object UserDiffCallback : DiffUtil.ItemCallback<UserData.Result>() {
        override fun areItemsTheSame(oldItem: UserData.Result, newItem: UserData.Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserData.Result, newItem: UserData.Result): Boolean {
            return oldItem == newItem
        }

    }

}
