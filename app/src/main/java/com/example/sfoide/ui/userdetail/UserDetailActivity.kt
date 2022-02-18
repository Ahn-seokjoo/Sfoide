package com.example.sfoide.ui.userdetail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.sfoide.R
import com.example.sfoide.databinding.ActivityUserDetailBinding
import com.example.sfoide.entities.UserData
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_detail)
        binding.lifecycleOwner = this

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        val item = intent.getParcelableExtra<UserData.Result>("userData")

        setUserData(item)
        setViewIntentClickListener(item)
    }

    @SuppressLint("SetTextI18n")
    private fun setUserData(item: UserData.Result?) {
        with(binding) {
            userDataList = item
            executePendingBindings()
        }
    }

    private fun setViewIntentClickListener(item: UserData.Result?) {
        binding.tvDetailPhoneNumber.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${item?.phone}"))
            startActivity(callIntent)
        }

        binding.tvDetailEmail.setOnClickListener {
            val addresses = arrayOf(item?.email)
            val emailIntent = Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_EMAIL, addresses)
                type = "plain/text"
            }
            startActivity(emailIntent)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val locationData = intent.getParcelableExtra<UserData.Result>("userData")?.location?.coordinates
        val location = locationData?.latitude?.toDouble()?.let { LatLng(it, locationData.longitude.toDouble()) }
        googleMap.addMarker(
            MarkerOptions()
                .position(location!!)
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location))
    }
}
