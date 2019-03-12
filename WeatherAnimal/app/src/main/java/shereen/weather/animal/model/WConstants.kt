package shereen.weather.animal.model

class WConstants {

    companion object {

        const val LOGGER = "WEATHER-ANIMAL"

        const val ARCTIC = "arctic"
        const val JUNGLE = "jungle"
        const val DESERT = "desert"
        const val DOMESTIC = "domestic"

        const val PREFS_FILE_NAME: String = "weather_preferences"
        const val FIRST_TIME : String = "first_time"
        const val STORED_THEME: String = "stored_theme"
        const val SAMPLED_THEME:String = "sampled_theme"

        const val ERROR_STR_1 = "error0"
        const val ERROR_STR_2 = "error1"
        const val ERROR_INT_1 = -100
        const val ERROR_INT_2 = -200


        var prefList = listOf(
            FIRST_TIME,
            STORED_THEME,
            SAMPLED_THEME
        )

        //Google places API
        const val PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place"
        const val TYPE_AUTOCOMPLETE = "/autocomplete"
        const val OUT_JSON = "/json"
        const val PLACES_API_KEY = "AIzaSyDUXAYlWMiWxDRgnekL_3zYehT0Avz0N50"

        //Open weather maps API
        const val WEATHER_API_BASE = "https://api.openweathermap.org"
        const val WEATHER_API_KEY = "f8fdae74c29544baebdb927d392c5538"

        const val GOOGLE = "Google"

        //Room constants
        const val DATABASE_NAME = "weather.database2"
        const val CITY_TABLE = "city"
        const val CITY_NAME = "name"
        const val CITY_TEMP = "temp"
        const val CITY_ICON = "icon"
    }
}