package com.example.sfoide.data.repository.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class UserDataDto(
    @SerializedName("results")
    val results: List<Result>,
) {
    @Parcelize
    data class Result(
        @SerializedName("cell")
        val cell: String,
        @SerializedName("dob")
        val dob: Dob,
        @SerializedName("email")
        val email: String,
        @SerializedName("gender")
        val gender: String,
        @SerializedName("location")
        val location: Location,
        @SerializedName("name")
        val name: Name,
        @SerializedName("phone")
        val phone: String,
        @SerializedName("picture")
        val picture: Picture,
    ) : Parcelable {
        @Parcelize
        data class Dob(
            @SerializedName("age")
            val age: Int,
        ) : Parcelable

        @Parcelize
        data class Location(
            @SerializedName("coordinates")
            val coordinates: Coordinates,
            @SerializedName("country")
            val country: String,
        ) : Parcelable {
            @Parcelize
            data class Coordinates(
                @SerializedName("latitude")
                val latitude: String,
                @SerializedName("longitude")
                val longitude: String,
            ) : Parcelable
        }

        @Parcelize
        data class Name(
            @SerializedName("first")
            val first: String,
            @SerializedName("last")
            val last: String,
        ) : Parcelable

        @Parcelize
        data class
        Picture(
            @SerializedName("large")
            val large: String,
            @SerializedName("medium")
            val medium: String,
            @SerializedName("thumbnail")
            val thumbnail: String,
        ) : Parcelable
    }
}
