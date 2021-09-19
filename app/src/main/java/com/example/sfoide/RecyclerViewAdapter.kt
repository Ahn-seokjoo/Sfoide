package com.example.sfoide

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.sfoide.databinding.UserItemBinding
import com.example.sfoide.entities.UserData

class RecyclerViewAdapter : ListAdapter<UserData.Result, RecyclerView.ViewHolder>(UserDiffCallback) {
    class UserViewHolder(private val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: UserData.Result) {
            with(binding) {
                Glide.with(ivUserImage)
                    .load(item.picture.thumbnail)
                    .transform(CircleCrop())
                    .into(ivUserImage)

                tvEmail.text = item.email
                tvHomeNumber.text = item.cell
                tvPhoneNumber.text = item.phone
                val name = item.name.last + item.name.first
                tvUserName.text = name
                tvUserAge.text = "(${item.dob.age})"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: UserItemBinding = UserItemBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener {
            Toast.makeText(parent.context, "${binding.tvUserName}", Toast.LENGTH_SHORT).show()
        }
        return UserViewHolder(binding)
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
