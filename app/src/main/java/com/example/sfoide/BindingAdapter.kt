package com.example.sfoide

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.sfoide.entities.UserData
import com.example.sfoide.enums.Country
import com.example.sfoide.enums.Gender
import com.example.sfoide.ui.userlist.adapter.UserListRecyclerViewAdapter

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("bindImage")
    fun ImageView.bindImage(url: String) {
        Glide.with(this)
            .load(url)
            .into(this)
    }

    @JvmStatic
    @BindingAdapter("bindLargeImage")
    fun ImageView.bindLargeImage(url: String) {
        Glide.with(this)
            .load(url)
            .transform(CenterCrop())
            .into(this)
    }

    @JvmStatic
    @BindingAdapter("bindUserList")
    fun RecyclerView.bindUserList(userList: LiveData<MutableList<UserData.Result>>?) {
        userList?.let {
            (adapter as UserListRecyclerViewAdapter).submitList(it.value?.toList())
        }
    }

    @JvmStatic
    @BindingAdapter("bindNameAndAge")
    fun TextView.bindNameAndAge(data: UserData.Result) {
        val name = data.name.last + data.name.first
        val gender = Gender.getGender(data.gender)
        val country = data.location?.country?.let { Country.getCountry(it) }

        this.text = "$name ${data.dob?.age} $gender $country"
    }

    @JvmStatic
    @BindingAdapter("bindEmail")
    fun TextView.bindEmail(email: String) {
        this.text = "\uD83D\uDCE7 $email"
    }

    @JvmStatic
    @BindingAdapter("bindCell")
    fun TextView.bindCell(cell: String) {
        this.text = "\u260E\uFE0F $cell"

    }

    @JvmStatic
    @BindingAdapter("bindPhone")
    fun TextView.bindPhone(phone: String) {
        this.text = "\uD83D\uDCF1 $phone"
    }
}
