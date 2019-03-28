package shereen.weather.animal.model.retrofit.entity

import shereen.weather.animal.model.retrofit.entity.simplewea.Main
import shereen.weather.animal.model.retrofit.entity.simplewea.Weather

class SimpleWeather(val weather: List<Weather>,
                    val main: Main,
                    val id: Long,
                    val name: String,
                    val cod: Int)