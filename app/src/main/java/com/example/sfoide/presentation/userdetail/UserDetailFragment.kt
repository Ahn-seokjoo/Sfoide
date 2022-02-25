package com.example.sfoide.presentation.userdetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.sfoide.R
import com.example.sfoide.data.repository.remote.UserDataDto
import com.example.sfoide.databinding.FragmentUserdetailBinding
import com.example.sfoide.domain.entities.UserData
import com.example.sfoide.domain.entities.toUserData
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailFragment : Fragment(R.layout.fragment_userdetail), OnMapReadyCallback {
    private lateinit var _binding: FragmentUserdetailBinding
    private val binding get() = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = DataBindingUtil.bind(view) ?: throw IllegalStateException("fail to bind")
        val item = arguments?.getParcelable<UserDataDto.Result>("userList")

        setUserData(item?.toUserData())
        setViewIntentClickListener(item?.toUserData())
        passOnMapData()
    }

    private fun passOnMapData() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    private fun setUserData(item: UserData?) {
        with(binding) {
            userDataList = item
            executePendingBindings()
        }
    }

    private fun setViewIntentClickListener(item: UserData?) {
        binding.tvDetailPhoneNumber.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${item?.phone}"))
            startActivity(callIntent)
        }

        binding.tvDetailEmail.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_EMAIL, arrayOf(item?.email))
                type = "plain/text"
            }
            startActivity(emailIntent)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val locationData = arguments?.getParcelable<UserDataDto.Result>("userList")?.location?.coordinates
        val location = LatLng(locationData?.latitude?.toDouble()!!, locationData.longitude.toDouble())
        googleMap.addMarker(
            MarkerOptions()
                .position(location)
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }
}
