package shereen.weather.animal.model.retrofit.entity.detailwea

class Detail(val dt: Long,
             val temp: Temp,
             val pressure: Double,
             val humidity: Int,
             val weather: List<Weather>,
             val speed: Double,
             val clouds: Int,
             val rain: Double)