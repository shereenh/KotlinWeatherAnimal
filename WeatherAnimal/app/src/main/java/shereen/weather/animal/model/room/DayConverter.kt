package shereen.weather.animal.model.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import shereen.weather.animal.model.room.entity.Day

class DayConverter {

    companion object {

        val gson = Gson()

        @TypeConverter
        @JvmStatic
        fun stringToDay(data: String): List<Day>{
            val listType = object:TypeToken<List<Day>>() {}.type
            return gson.fromJson(data, listType)
        }

        @TypeConverter
        @JvmStatic
        fun dayToString(dayList: List<Day>): String{
            return gson.toJson(dayList)
        }
    }


}