package com.example.sfoide.domain.entities

import com.example.sfoide.data.repository.remote.UserDataDto

fun UserDataDto.Result.toUserData() = UserData(
    age = dob.age,
    name = "${name.first} ${name.last}",
    email = email,
    cell = cell,
    phone = phone,
    gender = gender,
    country = location.country,
    location = location.coordinates.latitude to location.coordinates.longitude,
    imageList = picture.medium to picture.large trip picture.thumbnail
)

fun UserData.toUserDataDto() = UserDataDto.Result(
    cell = cell,
    email = email,
    gender = gender,
    location = UserDataDto.Result.Location(
        UserDataDto.Result.Location.Coordinates(latitude = location.first, longitude = location.second),
        country = country
    ),
    name = UserDataDto.Result.Name(first = this.name.split(" ")[0], last = this.name.split(" ")[1]),
    phone = phone,
    picture = UserDataDto.Result.Picture(medium = imageList.first, large = imageList.second, thumbnail = imageList.third),
    dob = UserDataDto.Result.Dob(age = age)
)

infix fun <A, B, C> Pair<A, B>.trip(thumbnail: C): Triple<A, B, C> = Triple(this.first, this.second, thumbnail)
