package com.example.sfoide.ext

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.sfoide.domain.entities.UserData
import com.example.sfoide.domain.entities.enums.Country
import com.example.sfoide.domain.entities.enums.Gender
import com.example.sfoide.presentation.userlist.recyclerview.UserListRecyclerViewAdapter

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("bindImageUrl")
    fun ImageView.bindImageUrl(url: String?) {
        Glide.with(this)
            .load(url)
            .transform(RoundedCorners(20))
            .into(this)
    }

    @JvmStatic
    @BindingAdapter("addInformation")
    fun TextView.addInformation(information: UserData) {
        this.text =
            "\u200E ${information.name}\u200E ${information.age} ${Gender.getGender(information.gender)} ${Country.getCountry(information.country)}"
    }

    @JvmStatic
    @BindingAdapter("addEmailImoji")
    fun TextView.addEmailImoji(email: String) {
        this.text = "\uD83D\uDCE7 $email"
    }

    @JvmStatic
    @BindingAdapter("addCellImoji")
    fun TextView.addCellImoji(cell: String) {
        this.text = "\u260E\uFE0F $cell"
    }

    @JvmStatic
    @BindingAdapter("addPhoneImoji")
    fun TextView.addPhoneImoji(phone: String) {
        this.text = "\uD83D\uDCF1 $phone"
    }

    @JvmStatic
    @BindingAdapter("bindData")
    fun RecyclerView.bindData(userList: LiveData<List<UserData>>?) {
        userList?.let {
            (adapter as UserListRecyclerViewAdapter).submitList(it.value?.toList())
        }
    }
}
