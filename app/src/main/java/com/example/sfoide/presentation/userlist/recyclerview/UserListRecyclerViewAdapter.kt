package com.example.sfoide.presentation.userlist.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sfoide.domain.entities.UserData

class UserListRecyclerViewAdapter(
    private val itemClick: (UserData) -> Unit,
) : ListAdapter<UserData, RecyclerView.ViewHolder>(UserDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return UserViewHolder(parent, itemClick)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as UserViewHolder).bind(getItem(position))
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        Glide.with(holder.itemView).clear(holder.itemView)
    }

    object UserDiffCallback : DiffUtil.ItemCallback<UserData>() {
        override fun areItemsTheSame(oldItem: UserData, newItem: UserData): Boolean { // 1 객체가 같은 주소 값인지?
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: UserData, newItem: UserData): Boolean { // 2 값이 다른지? 다르다면 작동
            return oldItem == newItem
        }
    }
}
