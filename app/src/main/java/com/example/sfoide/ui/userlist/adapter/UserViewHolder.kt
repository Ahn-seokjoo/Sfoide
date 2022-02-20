package com.example.sfoide.ui.userlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sfoide.databinding.ItemUserBinding
import com.example.sfoide.entities.UserData

class UserViewHolder(private val parent: ViewGroup, onItemClick: (UserData.Result) -> Unit) :
    RecyclerView.ViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false).root) {

    private lateinit var item: UserData.Result
    private val binding: ItemUserBinding = DataBindingUtil.bind(itemView) ?: throw IllegalStateException("fail to bind")

    init {
        itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    fun bind(item: UserData.Result) {
        this.item = item
        binding.userdata = item
        binding.executePendingBindings()
    }
}
