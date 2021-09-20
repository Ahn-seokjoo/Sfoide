package com.example.sfoide

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.sfoide.databinding.UserItemBinding
import com.example.sfoide.entities.UserData
import com.example.sfoide.ui.UserDetail
import kotlinx.coroutines.coroutineScope

private lateinit var data: UserData.Result
class RecyclerViewAdapter : ListAdapter<UserData.Result, RecyclerView.ViewHolder>(UserDiffCallback) {
    class UserViewHolder(private val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: UserData.Result) {
            data = item
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
            val intent = Intent(parent.context, UserDetail::class.java)
//            intent.putExtra("userData", arrayListOf(data))
            parent.context.startActivity(intent)
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
