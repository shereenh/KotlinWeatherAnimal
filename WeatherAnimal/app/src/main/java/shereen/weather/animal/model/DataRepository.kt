package shereen.weather.animal.model

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import shereen.weather.animal.model.network.PlacesAPI
import shereen.weather.animal.model.retrofit.RetrofitFactory
import shereen.weather.animal.model.retrofit.WeatherService
import shereen.weather.animal.model.room.WDatabase
import shereen.weather.animal.model.room.dao.CityDao
import shereen.weather.animal.model.room.entity.CityEntity
import java.lang.Exception

class DataRepository(private val prefs : SharedPreferences, private val mContext: Context){

    private var mPlacesAPI: PlacesAPI
    private var cityDao: CityDao
    private var sampledThemeLive = MutableLiveData<String>()

    var savedCities: LiveData<List<CityEntity>>

    init {
        sampledThemeLive.value = getStringSharedPref(WConstants.SAMPLED_THEME)
        mPlacesAPI = PlacesAPI(mContext)
        cityDao = WDatabase.getInstance(mContext)!!.cityDao()

        savedCities = cityDao.getAllCities()
    }

    // AUTOCOMPLETE

    fun autocompleteRequest(input: String){
        mPlacesAPI.autoPredict(input)
    }

    fun getAutoPredictions():MutableLiveData<List<String>>{
        return mPlacesAPI.mutablePlacesList
    }

    // ROOM

    fun saveCity(city: String){
        GlobalScope.launch{
            val query = async(Dispatchers.IO){
                cityDao.insertCity(CityEntity(name= city))
            }
            query.await()
        }
    }

    fun deleteAllCities(){
        GlobalScope.launch{
            val query = async(Dispatchers.IO){
                cityDao.deleteAllCities()
            }
            query.await()
        }
    }

    // RETROFIT

    fun requestSimpleWeather(){
        GlobalScope.launch(Dispatchers.Main) {
            val request = RetrofitFactory().getWeatherService().makeSimpleWeatherCall()
            try{
                val response = request.await()
                Log.d(WConstants.LOGGER, "GOT SOMETHING: " + response.toString())
                //convert to Room entity and save to db
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

