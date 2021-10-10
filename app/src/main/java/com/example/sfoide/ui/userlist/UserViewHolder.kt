package com.example.sfoide.ui.userlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.sfoide.databinding.ItemUserBinding
import com.example.sfoide.entities.UserData
import com.example.sfoide.enums.Country
import com.example.sfoide.enums.Gender

class UserViewHolder(private val parent: ViewGroup, onItemClick: (UserData.Result) -> Unit) :
    RecyclerView.ViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false).root) {
    private lateinit var item: UserData.Result
    private val binding = ItemUserBinding.bind(itemView)

    init {
        itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: UserData.Result) {
        this.item = item
        with(binding) {
            Glide.with(ivUserImage)
                .load(item.picture.thumbnail)
                .transform(CircleCrop())
                .into(ivUserImage)

            val name = item.name.last + item.name.first
            val gender = Gender.getGender(item.gender)
            val country = Country.getCountry(item.location!!.country)
            tvUserNameAge.text = "$name ${item.dob?.age} $gender $country"
            tvEmail.text = "\uD83D\uDCE7 ${item.email}"
            tvHomeNumber.text = "\u260E\uFE0F ${item.cell}"
            tvPhoneNumber.text = "\uD83D\uDCF1 ${item.phone}"
        }
    }

}
