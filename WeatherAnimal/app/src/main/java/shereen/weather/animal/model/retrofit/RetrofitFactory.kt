package shereen.weather.animal.model.retrofit

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import shereen.weather.animal.model.WConstants

class RetrofitFactory {

    private val weatherServiceApi: WeatherService
//
//    companion object {
//        fun getWeatherService(): WeatherService {
//            val retrofit : Retrofit = Retrofit.Builder()
//                .baseUrl(WConstants.WEATHER_API_BASE)
//                .addCallAdapterFactory(CoroutineCallAdapterFactory())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//
//            return retrofit.create(WeatherService::class.java)
//        }
//    }


    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(WConstants.WEATHER_API_BASE)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(UnsafeOkHttpClient().getUnsafeOkHttpClient())
            .build()
        weatherServiceApi = retrofit.create(WeatherService::class.java)
    }

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: RetrofitFactory? = null

        fun getInstance(): RetrofitFactory {
            return instance ?: synchronized(this) {
                instance ?: RetrofitFactory().also { instance = it }
            }
        }
    }


    fun getWeatherService(): WeatherService {
        return weatherServiceApi
    }

}

