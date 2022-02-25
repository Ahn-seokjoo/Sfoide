package com.example.sfoide.domain.entities.enums

enum class Gender(val gender: String, val genderData: String) {
    UNKNOWN("", ""),
    MALE("male", "\uD83D\uDE4B\u200D\u2642\uFE0F"),
    FEMALE("female", "\uD83D\uDE4B\u200D\u2640\uFE0F");

    companion object {
        fun getGender(gender: String): String {
            return when (gender) {
                MALE.gender -> MALE.genderData
                FEMALE.gender -> FEMALE.genderData
                else -> UNKNOWN.genderData
            }
        }
    }
}
