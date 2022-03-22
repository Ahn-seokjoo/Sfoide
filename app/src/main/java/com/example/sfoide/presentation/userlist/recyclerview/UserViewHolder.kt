package com.example.sfoide.presentation.userlist.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sfoide.databinding.ItemUserBinding
import com.example.sfoide.domain.entities.UserData

class UserViewHolder(private val parent: ViewGroup, onItemClick: (UserData) -> Unit) :
    RecyclerView.ViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false).root) {
    private lateinit var item: UserData
    private val binding: ItemUserBinding? = DataBindingUtil.bind(itemView)

    init {
        itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    fun bind(item: UserData) {
        this.item = item
        binding?.apply {
            userDataList = item
            executePendingBindings()
        }
    }

}
