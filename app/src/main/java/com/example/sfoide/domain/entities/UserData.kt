package com.example.sfoide.domain.entities

data class UserData(
    val age: Int,
    val name: String,
    val email: String,
    val cell: String,
    val phone: String,
    val gender: String,
    val country: String,
    val location: Pair<String, String>,
    val imageList: Triple<String, String, String>
)
