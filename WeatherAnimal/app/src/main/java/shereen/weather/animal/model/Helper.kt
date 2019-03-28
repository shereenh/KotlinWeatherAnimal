package shereen.weather.animal.model

import android.util.Log

import shereen.weather.animal.model.retrofit.entity.DetailWeather
import shereen.weather.animal.model.retrofit.entity.SimpleWeather

import shereen.weather.animal.model.room.entity.Hour


object Helper {

//    fun printAll(detail: DetailEntity){
//        var result = StringBuilder()
//        result.append("results:\n")
//        result.append(detail.name)
//        result.append("\n")
//        result.append(detail.day[0]!!.hourList[0].hour)
//        println(result)
//    }

    fun printHour(hour: Hour) {
        val result = StringBuilder()
        result.append(hour.hour)
        result.append(hour.max)
        println(result)

    }

    fun convertToWeatherE(simpleWea: SimpleWeather) {
        Log.d(WConstants.LOGGER, simpleWea.name)
//        Log.d(WConstants.LOGGER, simpleWea.weather[0].description)
    }

    fun showDetailE(detailWea: DetailWeather) {
        Log.d(WConstants.LOGGER, detailWea.cod)
//        Log.d(WConstants.LOGGER, detailWea.city.name)
//        Log.d(WConstants.LOGGER, detailWea.city.country)
    }

}

//    private fun getMet(metric: String):String{
//        return when(metric){
//            "F" -> "\u2109" + "F"
//            "C" -> "\u00b0"
//            else -> "-"
//        }
//    }

//    private fun makeHourList(data: List<Detail>, day: String): MutableList<Hour>{
//        var entityList = mutableListOf<Hour>()
//        for( item in data){
//            if(Helper.convertUnixToDay(item.dt) == day){
//                val entityItem = Hour(hour= Helper.convertUnixToHour(item.dt),
//                                    icon= WeatherIconSource.getWeaIcon(item.weather[0].description),
//                                    max= item.temp.max.roundToInt().toString() + WConstants.DEGREE,
//                                    min= item.temp.min.roundToInt().toString() + WConstants.DEGREE)
//                entityList.add(entityItem)
//            }
//        }
//        return entityList
//    }


//    private fun createDayList(data: List<Detail>): List<Day>{
//        var entityList = mutableListOf<Day>()
//        var tempPerDayMap: MutableMap<String, Double> = mutableMapOf()
//
//        for(item in data){
//            val day = Helper.convertUnixToDay(item.dt)
//            val temp = (item.temp.min + item.temp.max)/2
//            if(tempPerDayMap.containsKey(day)){
//                tempPerDayMap[day]  = (tempPerDayMap[day]!! + temp)/2
//            }else{
//                tempPerDayMap[day] = temp
//            }
//        }
//
//        for( item in tempPerDayMap){
//            var entityItem = Day(
//                day = item.key,
//                                 temp = item.value.roundToInt().toString() + WConstants.DEGREE,
//                                icon = WeatherIconSource.getWeaIcon("jc"),
//                                hourList = makeHourList(data, item.key))
//            entityList.add(entityItem)
//        }


//        for(item in data){
//            val entityData = Day(day= Helper.convertUnixToDay(item.dt),
////                                       hour = Helper.convertUnixToHour(item.dt),
//                                       icon = WeatherIconSource.getWeaIcon(item.weather[0].description),
////                                       maxTemp = item.temp.max.toString() + WConstants.DEGREE,
//                                       minTemp = item.temp.min.toString() + WConstants.DEGREE)
//            entityList.add(entityData)
//        }
//        return entityList
//    }

//    @ColumnInfo(name = WConstants.CITY_NAME) var name: String,
//    @ColumnInfo(name = WConstants.CITY_STATE) var state: String,
//    @ColumnInfo(name = WConstants.CITY_COUNTRY) var country: String,
//    @ColumnInfo(name = WConstants.CITY_TEMP) var temp: String,
//    @ColumnInfo(name = WConstants.CITY_MIN) var temp_min: String,
//    @ColumnInfo(name = WConstants.CITY_MAX) var temp_max: String,
//    @ColumnInfo(name = WConstants.CITY_DESCRIPTION) var description: String,
//    @ColumnInfo(name = WConstants.CITY_ICON) var icon: Int = R.drawable.sunny,

//    fun converterToCitySimple(simpleWea: SimpleWeather): CityEntity{
//        return CityEntity(name=simpleWea.name,
//                          state= "",
//                          country = simpleWea.main.
//                          temp = simpleWea.main.temp.roundToInt().toString() + WConstants.DEGREE,
//                          temp_max = simpleWea.main.temp_max.roundToInt().toString() + WConstants.DEGREE,
//                          temp_min = simpleWea.main.temp_min.roundToInt().toString() + WConstants.DEGREE,
//                          description = simpleWea.weather[0].description,
//                          icon = WeatherIconSource.getWeaIcon(simpleWea.weather[0].description))
//    }
//
//    fun convertToCityDetail(detailWea: DetailWeather): CityEntity{
//        return DetailEntity(name = simpleWea.name,
//                            temp = simpleWea.main.temp.roundToInt().toString() + WConstants.DEGREE,
//                            desc = simpleWea.weather[0].main,
//                            icon = WeatherIconSource.getWeaIcon(simpleWea.weather[0].description),
//                            min = simpleWea.main.temp_min.roundToInt().toString() + WConstants.DEGREE,
//                            max = simpleWea.main.temp_max.roundToInt().toString() + WConstants.DEGREE)
//    }
//
//    fun convertToDetailE(detailWea: DetailWeather): DetailEntity{
//        return DetailEntity(name= detailWea.city.name,
//                            country = detailWea.city.country,
//                            day = createDayList(detailWea.list).toMutableList())
//    }

//    fun convertUnixToDay(unix: Long): String {
//        return when{
//            Date(unix * 1000).day == 1 -> "Mon"
//            Date(unix * 1000).day == 2 -> "Tue"
//            Date(unix * 1000).day == 3 -> "Wed"
//            Date(unix * 1000).day == 4 -> "Thur"
//            Date(unix * 1000).day == 5 -> "Fri"
//            Date(unix * 1000).day == 6 -> "Sat"
//            Date(unix * 1000).day == 0 -> "Sun"
//            else -> Date(unix * 1000).day.toString()
//        }
//    }
//
//    fun convertUnixToHour(unix: Long): String {
//        val hourNow = Date(unix * 1000).hours
//        return if(hourNow < 13){
//            "$hourNow AM"
//        } else{
//            (hourNow-12).toString() + " PM"
//        }
//    }

//    private val gson = Gson()
//    private val mapType = object:TypeToken<HashMap<Long, Int>>() {}.type
//
//    fun hashToString(map: HashMap<Long, Int>):String{
//        return gson.toJson(map)
//    }
//
//    fun stringToHash(stored: String):HashMap<Long, Int>{
//        return gson.fromJson(stored, mapType)
//    }

//}