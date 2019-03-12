package shereen.weather.animal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import shereen.weather.animal.model.DataRepository
import shereen.weather.animal.model.WConstants
import shereen.weather.animal.model.room.entity.CityEntity

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val mRepository: DataRepository =
        DataRepository(application.getSharedPreferences(WConstants.PREFS_FILE_NAME, 0), application.applicationContext)

    var theme = mRepository.getStringSharedPref(WConstants.STORED_THEME)

    init {
//        checkFirstTime()
    }

    fun checkFirstTime():Boolean{
        return mRepository.checkFirstTime()
    }

    fun requestAutoPredictions(input: String){
        mRepository.autocompleteRequest(input)
    }

    fun getAutoPredictionList():MutableLiveData<List<String>>{
        return mRepository.getAutoPredictions()
    }

    fun saveCity(city: String){
        mRepository.saveCity(city)
        requestSimpleWeather(city)
    }

    fun getsavedCities(): LiveData<List<CityEntity>>{
        return mRepository.savedCities
    }

    fun deleteAllCities(){
        mRepository.deleteAllCities()
    }

    fun requestSimpleWeather(city: String){
        mRepository.requestSimpleWeather()
    }

}