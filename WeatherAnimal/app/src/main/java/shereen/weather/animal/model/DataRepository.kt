package shereen.weather.animal.model

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import shereen.weather.animal.model.network.PlacesAPI
import shereen.weather.animal.model.retrofit.RetrofitFactory
import shereen.weather.animal.model.retrofit.WeatherService
import shereen.weather.animal.model.retrofit.entity.SimpleWeather
import shereen.weather.animal.model.room.WDatabase
import shereen.weather.animal.model.room.dao.CityDao
import shereen.weather.animal.model.room.entity.CityEntity
import shereen.weather.animal.model.room.entity.Day
import shereen.weather.animal.model.source.CityFactory
import java.lang.Exception
import java.util.*

class DataRepository(private val prefs : SharedPreferences, private val mContext: Context){

    private var weatherService: WeatherService = RetrofitFactory().getWeatherService()
    private var mPlacesAPI: PlacesAPI = PlacesAPI(mContext)
    private var cityDao: CityDao = WDatabase.getInstance(mContext)!!.cityDao()

    private var sampledThemeLive = MutableLiveData<String>()
    var savedCities: LiveData<List<CityEntity>>

    init {
        sampledThemeLive.value = getStringSharedPref(WConstants.SAMPLED_THEME)
        savedCities = cityDao.getAllCities()
    }

    // UTILITY

    fun refreshAllCitiesSimple(){  //call at begining

//        GlobalScope.launch(Dispatchers.IO) {
//            val request = cityDao.getCityNames()
//            try{
//                val cityList = request.await()
//                for(city in cityList){
//                    requestSimpleWeather(city)
//                }
//            }catch (e: Exception){
//                Log.d(WConstants.LOGGER, e.toString())
//            }
//        }
    }

    // AUTOCOMPLETE

    fun autocompleteRequest(input: String){
        mPlacesAPI.autoPredict(input)
    }

    fun getAutoPredictions():MutableLiveData<List<String>>{
        return mPlacesAPI.mutablePlacesList
    }

    fun clearAutoCompletePredictions(){
        mPlacesAPI.clearList()
    }

    // ROOM

    fun deleteAllCities(){
        GlobalScope.launch{
            val query = async(Dispatchers.IO){
                cityDao.deleteAllCities()
            }
            query.await()
        }
    }

//    fun insertCity(city: CityEntity){
//        GlobalScope.launch{
//            val query = async(Dispatchers.IO){
//                cityDao.insertCity(city)
//            }
//            query.await()
//            updateTempData(cityStateCountry, response)
//
//        }
//    }

    private fun updateTempData(location: String, simpleWeather: SimpleWeather){
        GlobalScope.launch{
            val query = async(Dispatchers.IO){
                val locationList = location.split(",")
                cityDao.updateTempDetails(locationList[0], locationList[1], locationList[2], simpleWeather.main.temp, simpleWeather.main.temp_min,
                    simpleWeather.main.temp_max, simpleWeather.weather[0].main)
                requestDetailWeather(location)
            }
            query.await()
        }
    }

    fun deleteCity(city: String){
        GlobalScope.launch{
            val query = async(Dispatchers.IO){
                val cityList = city.split(",")
                cityDao.deleteCity(cityList[0], cityList[1], cityList[2])
            }
            query.await()
        }
    }

//    fun saveDetail(detail: DetailEntity, updateData: DetailEntity){
//        GlobalScope.launch{
//            val query = async(Dispatchers.IO){
//                detailDao.insertCity(detail)
//                detailDao.updateCity(updateData.name, updateData.temp, updateData.min, updateData.max, updateData.icon, updateData.desc)
//            }
//            query.await()
//        }
//    }

    fun updateDayList(location: String, dayList: List<Day>){
        GlobalScope.launch{
            val query = async(Dispatchers.IO){
                val locationList = location.split(",")
                cityDao.updateDayList(dayList, locationList[0], locationList[1], locationList[2])
            }
            query.await()
        }
    }

//    fun updateDetail(detail: DetailEntity){
//        GlobalScope.launch{
//            val query = async(Dispatchers.IO){
//                detailDao.updateCity(detail.name, detail.temp)
//            }
//            query.await()
//        }
//    }

    // RETROFIT

    fun requestSimpleWeather(cityStateCountry: String){
        GlobalScope.launch(Dispatchers.IO) {
            val request = weatherService.makeSimpleWeatherCall(cityStateCountry)
            try{
                val response = request.await()
                Log.d(WConstants.LOGGER, "GOT SOMETHING: " + response)
                //convert to Room entity and save to db
                if(response.cod == 200){
                    val loc = cityStateCountry.split(",")
                    if(loc.size == 3){
                        cityDao.insertCity(CityFactory.convertLocationToCityE(loc))
                        updateTempData(cityStateCountry, response)
                    }
                }
            }catch (e: Exception){
                Log.d(WConstants.LOGGER, e.toString())
            }
        }
    }

    fun requestDetailWeather(city: String){
        GlobalScope.launch(Dispatchers.IO) {
            val request = weatherService.makeDetailWeatherCall(city)
            try{
                val response = request.await()
                Log.d(WConstants.LOGGER, "GOT SOMETHING 123: " + response)
//                Helper.showDetailE(response)
                //convert to Room entity and save to db
//                saveDetail(Helper.convertToDetailE(response), updateData)
                if(response.cod == "200"){
                    updateDayList(city, CityFactory.convertDetailToDayList(response))
                }
            }catch (e: Exception){
                Log.d(WConstants.LOGGER, e.toString())
            }
        }
    }


//    fun requestAutcompleteList(input: String){
//        GlobalScope.launch(Dispatchers.Main) {
//            val request = retroPlaceService.getMyPredictions(WConstants.PLACES_API_KEY, input)
//            try{
//                val response = request.await()
//                Log.d(WConstants.LOGGER, "Got response9: " + response.body().toString())
//                Log.d(WConstants.LOGGER, "MESSAGE: " + response.body()!!.status)
//            }catch (e: Exception){
//                Log.d(WConstants.LOGGER, e.toString())
//            }
//        }
//    }

    //Weather API

//    fun requestSampleWeather(){
//        GlobalScope.launch(Dispatchers.Main){
//            val request = retrofitService.makeSampleWeatherCall()
//            try{
//                val response = request.await()
//                Log.d(WConstants.LOGGER, response.body().toString())
//                //save to db
//                if (response.body() != null) {
////                    insertAfterDelete(response.body()!!)
//                }
//            } catch(e: Exception){
//                Log.d(WConstants.LOGGER, e.toString())
//            }
//        }
//    }

    // Shared Preferences

    fun checkFirstTime(): Boolean{
        val firstTime =  prefs.getBoolean(WConstants.FIRST_TIME, true)
        if(firstTime){
            putSharedPref(WConstants.FIRST_TIME, false)
            putSharedPref(WConstants.SAMPLED_THEME, WConstants.JUNGLE) //set selected theme as jungle in theme selector
            putSharedPref(WConstants.STORED_THEME, WConstants.JUNGLE)  //set default first ever theme to jungle
            putSharedPref(WConstants.TEMP_METRIC, "C")
        }
//        isFirstTime.postValue(firstTime)
        return firstTime
    }

    fun getSampledThemeLive(): MutableLiveData<String>{
        return sampledThemeLive
    }


    fun putSharedPref(key: String, value: Any){
        if ( key in WConstants.prefList){
            val editor = prefs.edit()
            if(value is String){
                editor.putString(key, value)
            }else if(value is Int){
                editor.putInt(key, value)
            }else if(value is Long){

            }else if(value is Float){

            }else if(value is Boolean){
                editor.putBoolean(key, value)
            }
            editor.apply()
            postLiveData(key, value)
        }
    }

    fun getStringSharedPref(key: String):String{
        if(key in WConstants.prefList){
            return prefs.getString(key, WConstants.ERROR_STR_1)!!
        }
        return WConstants.ERROR_STR_2
    }

    fun getIntSharedPref(key: String):Int{
        if(key in WConstants.prefList){
            return prefs.getInt(key, WConstants.ERROR_INT_1)
        }
        return WConstants.ERROR_INT_2
    }

    private fun postLiveData(key: String, value: Any){
        when(key){
            WConstants.SAMPLED_THEME -> sampledThemeLive.postValue(getStringSharedPref(WConstants.SAMPLED_THEME))
            else -> Log.d(WConstants.LOGGER, "postLiveData nothing matched")
        }
    }
}

