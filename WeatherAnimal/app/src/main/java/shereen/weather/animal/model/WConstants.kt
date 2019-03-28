package shereen.weather.animal.model

object WConstants {

        const val LOGGER = "WEATHER-ANIMAL"
        const val LOG = "CURRENT-LOG"

        const val ARCTIC = "arctic"
        const val JUNGLE = "jungle"
        const val DESERT = "desert"
        const val DOMESTIC = "domestic"

        const val PREFS_FILE_NAME: String = "weather_preferences"
        const val FIRST_TIME : String = "first_time"
        const val STORED_THEME: String = "stored_theme"
        const val SAMPLED_THEME:String = "sampled_theme"
        const val TEMP_METRIC = "C"
        const val CITY_ORDER = "city_order"

        const val ERROR_STR_1 = "error0"
        const val ERROR_STR_2 = "error1"
        const val ERROR_INT_1 = -100
        const val ERROR_INT_2 = -200


        var prefList = listOf(
            FIRST_TIME,
            STORED_THEME,
            SAMPLED_THEME,
            TEMP_METRIC
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
        const val DATABASE_NAME = "weather.database1"

        const val CITY_TABLE = "city"
        const val CITY_NAME = "name"
        const val CITY_STATE = "state"
        const val CITY_COUNTRY = "country"
        const val CITY_TEMP = "temp"
        const val CITY_MIN = "temp_min"
        const val CITY_MAX = "temp_max"
        const val CITY_ICON = "icon"
        const val CITY_DESCRIPTION = "description"
        const val CITY_LIST = "day_list"

        const val DEGREE = "\u00b0"

        const val HOME = "home"
        const val SEARCH = "menu_items"
        const val QUICK = "quick"
        const val SETTINGS = "settings"

}