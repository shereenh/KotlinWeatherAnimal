package shereen.weather.animal.model.source

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

object Utils {

    private val gson = Gson()
    private val mapType = object: TypeToken<HashMap<Long, Int>>() {}.type

    fun hashToString(map: HashMap<Long, Int>):String{
        return gson.toJson(map)
    }

    fun stringToHash(stored: String):HashMap<Long, Int>{
        return gson.fromJson(stored, mapType)
    }

    fun convertUnixToDay(unix: Long): String {
        return when{
            Date(unix * 1000).day == 1 -> "Mon"
            Date(unix * 1000).day == 2 -> "Tue"
            Date(unix * 1000).day == 3 -> "Wed"
            Date(unix * 1000).day == 4 -> "Thur"
            Date(unix * 1000).day == 5 -> "Fri"
            Date(unix * 1000).day == 6 -> "Sat"
            Date(unix * 1000).day == 0 -> "Sun"
            else -> Date(unix * 1000).day.toString()
        }
    }

    fun convertUnixToHour(unix: Long): String {
        val day = Date(unix*1000).toString()
        return day
    }

    fun convertUnixToDate(unix: Long):String {


        val hourNow = Date(unix * 1000).hours
        return if(hourNow < 13){
            "$hourNow AM"
        } else{
            (hourNow-12).toString() + " PM"
        }
    }

}