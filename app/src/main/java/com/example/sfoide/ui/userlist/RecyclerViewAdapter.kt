package com.example.sfoide.ui.userlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sfoide.databinding.ItemUserBinding
import com.example.sfoide.entities.UserData

class RecyclerViewAdapter(
    private val onItemClick: (UserData.Result) -> Unit,
) : ListAdapter<UserData.Result, RecyclerView.ViewHolder>(UserDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemUserBinding = ItemUserBinding.inflate(inflater, parent, false)
        return UserViewHolder(binding, onItemClick)
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
