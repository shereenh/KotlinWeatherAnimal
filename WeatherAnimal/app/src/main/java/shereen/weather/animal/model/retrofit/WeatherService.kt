package shereen.weather.animal.model.retrofit

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import shereen.weather.animal.model.WConstants
import shereen.weather.animal.model.entity.ResponseEntity
import shereen.weather.animal.model.retrofit.entity.SimpleWeather

interface WeatherService {

    @GET("/data/2.5/forecast/daily?q=32789&mode=json&units=metric&cnt=14&appid="+ WConstants.WEATHER_API_KEY)
    fun makeSampleWeatherCall(): Deferred<ResponseEntity>

    @GET("/data/2.5/weather?q=32789&mode=json&units=metric&cnt=14&appid=f8fdae74c29544baebdb927d392c5538")
    fun makeSimpleWeatherCall(): Deferred<SimpleWeather>

}