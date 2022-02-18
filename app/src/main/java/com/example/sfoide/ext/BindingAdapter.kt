package com.example.sfoide.ext

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.sfoide.entities.UserData
import com.example.sfoide.enums.Country
import com.example.sfoide.enums.Gender
import com.example.sfoide.ui.userlist.adapter.UserListRecyclerViewAdapter

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
    fun TextView.addInformation(information: UserData.Result) {
        val name = information.name?.last + information.name?.first
        val country = information.location?.country?.let { Country.getCountry(it) }
        val gender = information.gender?.let { Gender.getGender(it) }
        this.text = "$name ${information.dob?.age} $gender $country"
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
    fun RecyclerView.bindData(userList: LiveData<List<UserData.Result>>?) {
        userList?.let {
            (adapter as UserListRecyclerViewAdapter).submitList(it.value?.toMutableList())
        }
    }
}
