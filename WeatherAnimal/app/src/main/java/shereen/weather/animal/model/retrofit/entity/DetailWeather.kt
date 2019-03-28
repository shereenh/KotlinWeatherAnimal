package shereen.weather.animal.model.retrofit.entity

import shereen.weather.animal.model.retrofit.entity.detailwea.City
import shereen.weather.animal.model.retrofit.entity.detailwea.Detail

class DetailWeather(val city: City,
                    val cod: String,
                    val cnt: Int,
                    val list: List<Detail>)