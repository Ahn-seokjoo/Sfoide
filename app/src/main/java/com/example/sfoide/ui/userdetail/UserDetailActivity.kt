package com.example.sfoide.ui.userdetail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.sfoide.R
import com.example.sfoide.databinding.ActivityUserDetailBinding
import com.example.sfoide.entities.UserData
import com.example.sfoide.enums.Country
import com.example.sfoide.enums.Gender
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class UserDetailActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        val item = intent.getParcelableExtra<UserData.Result>("userData")

        setUserData(item)
        setViewIntentClickListener(item)
    }

    @SuppressLint("SetTextI18n")
    private fun setUserData(item: UserData.Result?) {
        with(binding) {
            Glide.with(ivDetailUserImage)
                .load(item?.picture?.large)
                .transform(CircleCrop())
                .into(ivDetailUserImage)

            val name = item?.name?.last + item?.name?.first
            val gender = item?.gender?.let { Gender.getGender(it) }
            val country = item?.location?.country?.let { Country.getCountry(it) }
            tvUserDetailNameText.text = "$name ${item?.dob?.age} $gender $country"
            tvDetailEmail.text = "\uD83D\uDCE7 ${item?.email}"
            tvDetailHomeNumber.text = "\u260E\uFE0F ${item?.cell}"
            tvDetailPhoneNumber.text = "\uD83D\uDCF1 ${item?.phone}"
        }
    }

    private fun setViewIntentClickListener(item: UserData.Result?) {
        binding.tvDetailPhoneNumber.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${item?.phone}"))
            startActivity(callIntent)
        }

        binding.tvDetailEmail.setOnClickListener {
            var addresses = arrayOf(item?.email)
            val emailIntent = Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_EMAIL, addresses)
                type = "plain/text"
            }
            startActivity(emailIntent)
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        val locationData = intent.getParcelableExtra<UserData.Result>("userData")?.location?.coordinates
        val location = locationData?.latitude?.toDouble()?.let { LatLng(it, locationData.longitude.toDouble()) }
        googleMap?.addMarker(
            MarkerOptions()
                .position(location)
        )
        googleMap?.moveCamera(CameraUpdateFactory.newLatLng(location))
    }
}
