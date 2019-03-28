package shereen.weather.animal.model.source

import shereen.weather.animal.R
import shereen.weather.animal.model.WConstants
import shereen.weather.animal.model.retrofit.entity.DetailWeather
import shereen.weather.animal.model.retrofit.entity.detailwea.Detail
import shereen.weather.animal.model.room.entity.CityEntity
import shereen.weather.animal.model.room.entity.Day
import shereen.weather.animal.model.room.entity.Hour
import shereen.weather.animal.viewmodel.entity.CityView
import shereen.weather.animal.viewmodel.entity.DayView
import shereen.weather.animal.viewmodel.entity.DetailView
import shereen.weather.animal.viewmodel.entity.HourView
import kotlin.math.roundToInt

object CityFactory {

    fun convertLocationToCityE(locList: List<String>): CityEntity{
        return CityEntity(name = locList[0], state = locList[1], country = locList[2])
    }

    fun convertDetailToDayList(detailWeather: DetailWeather): List<Day>{
        var dayList: MutableList<Day> = mutableListOf()
        dayList.add(Day("",0.0, hourList = makeHourList(detailWeather.list)))
        return dayList
    }

    fun makeHourList(detailList: List<Detail>):MutableList<Hour>{
        var hourList: MutableList<Hour>  = mutableListOf()
        for(detail in detailList){
            hourList.add(Hour(hour= Utils.convertUnixToHour(detail.dt), dt = detail.dt, max = detail.temp.max, min = detail.temp.min))
        }
        return hourList
    }

    fun convertCityView(city: CityEntity): CityView{
        return CityView(city.name, city.state, city.country,
            city.temp.roundToInt().toString() + WConstants.DEGREE,
            city.temp_min.roundToInt().toString() + WConstants.DEGREE,
            city.temp_max.roundToInt().toString() + WConstants.DEGREE,
            city.description,
            WeatherIconSource.getWeaIcon(city.description))
    }

    fun convertCityViewList(cityList: List<CityEntity>): List<CityView>{
        var cityViewList: MutableList<CityView> = mutableListOf()
        for(city in cityList){
            cityViewList.add(convertCityView(city))
        }
        return cityViewList
    }

    fun makeDayViewList(dayList: List<Day>): MutableList<DayView>{
        var result: MutableList<DayView> = mutableListOf()
        for(day in dayList){
            result.add(DayView(day=day.day, icon = R.drawable.rainbow, temp = day.temp.roundToInt().toString() + WConstants.DEGREE,
                hourList = makeHourViewList(day.hourList)))
        }
        return result
    }

    fun makeHourViewList(hourList: List<Hour>): MutableList<HourView>{
        var result :MutableList<HourView> = mutableListOf()
        for(hour in hourList){
            result.add(HourView(hour = hour.hour, icon = R.drawable.rainbow, max = hour.max.roundToInt().toString() + WConstants.DEGREE,
                min = hour.min.roundToInt().toString() + WConstants.DEGREE))
        }
        return result
    }

    fun convertDetailView(cityEntity: CityEntity): DetailView{
        return DetailView(cityView = convertCityView(cityEntity), day = makeDayViewList(cityEntity.day))
    }

    fun convertDetailViewList(detailList: List<CityEntity>): List<DetailView>{
        var detailViewList: MutableList<DetailView> = mutableListOf()
        for(city in detailList){
            detailViewList.add(convertDetailView(city))
        }
        return detailViewList
    }

}
