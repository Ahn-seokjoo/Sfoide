package com.example.sfoide.ui.userdetail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.sfoide.databinding.ActivityUserDetailBinding
import com.example.sfoide.entities.UserData
import com.example.sfoide.enums.Country
import com.example.sfoide.enums.Gender

class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val item = intent.getParcelableExtra<UserData.Result>("userData")

        with(binding) {
            Glide.with(ivDetailUserImage)
                .load(item?.picture?.large)
                .transform(CircleCrop())
                .into(ivDetailUserImage)

            tvDetailEmail.text = ("\uD83D\uDCE7" + item?.email)
            tvDetailHomeNumber.text = ("\u260E\uFE0F" + item?.cell)
            tvDetailPhoneNumber.text = ("\uD83D\uDCF1" + item?.phone)
            val name = item?.name?.last + item?.name?.first
            val gender = Gender.getGender(item!!.gender)
            val country = Country.getCountry(item.location!!.country)
            tvUserDetailNameText.text = name + "(${item.dob?.age})" + gender + country
        }
    }
}
