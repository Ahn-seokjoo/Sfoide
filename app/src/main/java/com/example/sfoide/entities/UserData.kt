package com.example.sfoide.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

data class UserData(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val results: List<Result>,
) {
    data class Info(
        @SerializedName("page")
        val page: Int,
        @SerializedName("results")
        val results: Int,
        @SerializedName("seed")
        val seed: String,
        @SerializedName("version")
        val version: String?,
    )

    @Parcelize
    data class Result(
        @SerializedName("cell")
        val cell: String? = null,
        @SerializedName("dob")
        val dob: Dob? = null,
        @SerializedName("email")
        val email: String? = null,
        @SerializedName("gender")
        val gender: String? = null,
        @SerializedName("id")
        val id: Id? = null,
        @SerializedName("location")
        val location: Location? = null,
        @SerializedName("login")
        val login: Login? = null,
        @SerializedName("name")
        val name: Name? = null,
        @SerializedName("nat")
        val nat: String? = null,
        @SerializedName("phone")
        val phone: String? = null,
        @SerializedName("picture")
        val picture: Picture? = null,
        @SerializedName("registered")
        val registered: Registered? = null,
    ) : Parcelable {
        @Parcelize
        data class Dob(
            @SerializedName("age")
            val age: Int,
            @SerializedName("date")
            val date: String,
        ) : Parcelable

        @Parcelize
        data class Id(
            @SerializedName("name")
            val name: String?,
            @SerializedName("value")
            val value: @RawValue Any?,
        ) : Parcelable

        @Parcelize
        data class Location(
            @SerializedName("city")
            val city: String,
            @SerializedName("coordinates")
            val coordinates: Coordinates,
            @SerializedName("country")
            val country: String,
            @SerializedName("postcode")
            val postcode: String,
            @SerializedName("state")
            val state: String,
            @SerializedName("street")
            val street: Street,
            @SerializedName("timezone")
            val timezone: Timezone,
        ) : Parcelable {
            @Parcelize
            data class Coordinates(
                @SerializedName("latitude")
                val latitude: String,
                @SerializedName("longitude")
                val longitude: String,
            ) : Parcelable

            @Parcelize
            data class Street(
                @SerializedName("name")
                val name: String,
                @SerializedName("number")
                val number: Int,
            ) : Parcelable

            @Parcelize
            data class Timezone(
                @SerializedName("description")
                val description: String,
                @SerializedName("offset")
                val offset: String,
            ) : Parcelable
        }

        @Parcelize
        data class Login(
            @SerializedName("md5")
            val md5: String,
            @SerializedName("password")
            val password: String,
            @SerializedName("salt")
            val salt: String,
            @SerializedName("sha1")
            val sha1: String,
            @SerializedName("sha256")
            val sha256: String,
            @SerializedName("username")
            val username: String,
            @SerializedName("uuid")
            val uuid: String,
        ) : Parcelable

        @Parcelize
        data class Name(
            @SerializedName("first")
            val first: String,
            @SerializedName("last")
            val last: String,
            @SerializedName("title")
            val title: String,
        ) : Parcelable

        @Parcelize
        data class Picture(
            @SerializedName("large")
            val large: String,
            @SerializedName("medium")
            val medium: String,
            @SerializedName("thumbnail")
            val thumbnail: String,
        ) : Parcelable

        @Parcelize
        data class Registered(
            @SerializedName("age")
            val age: Int,
            @SerializedName("date")
            val date: String,
        ) : Parcelable
    }
}
