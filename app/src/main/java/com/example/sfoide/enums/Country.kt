package com.example.sfoide.enums

enum class Country(val country: String, val countryEmoji: String) {
    UNKNOWN("", ""),
    AU("Australia", "\uD83C\uDDE6\uD83C\uDDFA"),
    BR("Brazil", "\uD83C\uDDE7\uD83C\uDDF7"),
    CA("Canada", "\uD83C\uDDE8\uD83C\uDDE6"),
    CH("Switzerland", "\uD83C\uDDE8\uD83C\uDDED"),
    DE("Germany", "\uD83C\uDDE9\uD83C\uDDEA"),
    DK("Denmark", "\uD83C\uDDE9\uD83C\uDDF0"),
    ES("Spain", "\uD83C\uDDEA\uD83C\uDDF8"),
    FI("Finland", "\uD83C\uDDEB\uD83C\uDDEE"),
    FR("France", "\uD83C\uDDEB\uD83C\uDDF7"),
    GB("United Kingdom", "\uD83C\uDDEC\uD83C\uDDE7"),
    IE("Ireland", "\uD83C\uDDEE\uD83C\uDDEA"),
    IR("Iran", "\uD83C\uDDEE\uD83C\uDDF7"),
    NO("Norway", "\uD83C\uDDF3\uD83C\uDDF4"),
    NL("Netherlands", "\uD83C\uDDF3\uD83C\uDDF1"),
    NZ("New Zealand", "\uD83C\uDDF3\uD83C\uDDFF"),
    TR("Turkey", "\uD83C\uDDF9\uD83C\uDDF7"),
    US("United States", "\uD83C\uDDFA\uD83C\uDDF8");

    companion object {
        fun getCountry(country: String): String {
            return when (country) {
                AU.country -> AU.countryEmoji
                BR.country -> BR.countryEmoji
                CA.country -> CA.countryEmoji
                CH.country -> CH.countryEmoji
                DE.country -> DE.countryEmoji
                DK.country -> DK.countryEmoji
                ES.country -> ES.countryEmoji
                FI.country -> FI.countryEmoji
                FR.country -> FR.countryEmoji
                GB.country -> GB.countryEmoji
                IE.country -> IE.countryEmoji
                IR.country -> IR.countryEmoji
                NO.country -> NO.countryEmoji
                NL.country -> NL.countryEmoji
                NZ.country -> NZ.countryEmoji
                TR.country -> TR.countryEmoji
                US.country -> US.countryEmoji
                else -> UNKNOWN.countryEmoji // United States
            }
        }
    }
}



