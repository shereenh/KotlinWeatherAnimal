package shereen.weather.animal.viewmodel

import android.app.Application
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.*
import androidx.lifecycle.Transformations.map
import shereen.weather.animal.model.DataRepository
import shereen.weather.animal.model.WConstants
import shereen.weather.animal.model.room.entity.CityEntity
import shereen.weather.animal.model.source.CityFactory
import shereen.weather.animal.viewmodel.entity.CityView
import shereen.weather.animal.viewmodel.entity.DetailView

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val mRepository: DataRepository =
        DataRepository(application.getSharedPreferences(WConstants.PREFS_FILE_NAME, 0), application.applicationContext)

    var theme = mRepository.getStringSharedPref(WConstants.STORED_THEME)

    var currentFragment = MutableLiveData<String>()
    var currentWeatherIndex = MutableLiveData<Int>()

    var cityViewList: LiveData<List<CityView>>
    var detailViewList: LiveData<List<DetailView>>

    lateinit var menuItem: MenuItem

    init {
//        checkFirstTime()
        currentFragment.value = WConstants.QUICK
        cityViewList = Transformations.map(mRepository.savedCities) {data -> CityFactory.convertCityViewList(data)}
        detailViewList = Transformations.map(mRepository.savedCities) {data -> CityFactory.convertDetailViewList(data)}
    }

    fun changeCurrentFragment(fragmentType: String){
        currentFragment.value = fragmentType
    }

    fun saveMenuItem(item: MenuItem){
        menuItem = item
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

    fun clearAutoPredictions() = mRepository.clearAutoCompletePredictions()

    fun saveCity(city: String){
        mRepository.requestSimpleWeather(city)
    }

//    fun getsavedCities(): LiveData<List<CityEntity>>{
//        return mRepository.savedCities
//    }

//    fun getCityViewList(): MutableLiveData<List<CityView>>{
//        return Transformations.map(mRepository.savedCities, savedCities -> {
//            return CityFactory.convertCityViewList(savedCities)
//        })
//    }

    fun deleteCity(city: String){
        mRepository.deleteCity(city)
    }

//    fun getDetailCities() = mRepository.savedDetails

    fun deleteAllCities(){
        mRepository.deleteAllCities()
    }

    fun refreshCities(){
        mRepository.refreshAllCitiesSimple()
    }

}