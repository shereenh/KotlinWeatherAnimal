package shereen.weather.animal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import shereen.weather.animal.model.DataRepository
import shereen.weather.animal.model.WConstants

class ClimateViewModel(application: Application): AndroidViewModel(application) {

    private val mRepository: DataRepository =
        DataRepository(application.getSharedPreferences(WConstants.PREFS_FILE_NAME, 0), application.applicationContext)
    var sampledThemeLive = MutableLiveData<String>()

    init{
        sampledThemeLive = mRepository.getSampledThemeLive()
    }

    fun changeSampledTheme(theme: String){
        mRepository.putSharedPref(WConstants.SAMPLED_THEME, theme)
    }

    fun changeStoredTheme(theme: String){
        mRepository.putSharedPref(WConstants.STORED_THEME, theme)
    }

}