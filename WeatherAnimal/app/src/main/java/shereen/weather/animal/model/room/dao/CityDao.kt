package shereen.weather.animal.model.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import io.reactivex.Single
import kotlinx.coroutines.Deferred
import shereen.weather.animal.model.WConstants
import shereen.weather.animal.model.room.entity.CityEntity
import shereen.weather.animal.model.room.entity.Day

@Dao
interface CityDao {

    @Insert(onConflict = REPLACE)
    fun insertCity(city: CityEntity)

    @Query("SELECT * FROM " + WConstants.CITY_TABLE)
    fun getAllCities(): LiveData<List<CityEntity>>

//    @Query("SELECT "+WConstants.CITY_NAME + " FROM " + WConstants.CITY_TABLE)
//    fun getCityNames(): Deferred<List<String>>

    @Query("UPDATE " + WConstants.CITY_TABLE + " SET " + WConstants.CITY_LIST + "=:dayListString WHERE "
            + WConstants.CITY_NAME + "=:cityName AND "
            + WConstants.CITY_STATE + "=:cityState AND "
            + WConstants.CITY_COUNTRY + "=:cityCountry")
    fun updateDayList(dayListString: List<Day>, cityName: String, cityState: String, cityCountry: String)

    @Query("UPDATE " + WConstants.CITY_TABLE + " SET " + WConstants.CITY_TEMP + "=:temp AND "
            + WConstants.CITY_MIN + "=:temp_min AND "
            + WConstants.CITY_MAX + "=:temp_max AND "
            + WConstants.CITY_DESCRIPTION + "=:description WHERE "
            + WConstants.CITY_NAME + "=:cityName AND "
            + WConstants.CITY_STATE + "=:cityState AND "
            + WConstants.CITY_COUNTRY + "=:cityCountry")
    fun updateTempDetails(cityName: String, cityState: String, cityCountry: String, temp: Double, temp_min: Double, temp_max: Double, description: String)

    @Query("DELETE FROM " + WConstants.CITY_TABLE + " WHERE " +
            WConstants.CITY_NAME + "=:city AND " +
            WConstants.CITY_STATE + "=:state AND " +
            WConstants.CITY_COUNTRY + "=:country")
    fun deleteCity(city: String, state: String, country: String)

    @Query("DELETE FROM " + WConstants.CITY_TABLE)
    fun deleteAllCities()
}

//@Query("UPDATE " + WConstants.DETAIL_TABLE + " SET " +
//        WConstants.DETAIL_TEMP + " =:temp," +
//        WConstants.DETAIL_MAX + "=:max," +
//        WConstants.DETAIL_MIN + "=:min," +
//        WConstants.DETAIL_DESC + "=:desc," +
//        WConstants.DETAIL_ICON + "=:icon " +
//        "WHERE " + WConstants.DETAIL_NAME + " =:city")
//fun updateCity(city: String, temp: String, min: String, max: String, icon: Int, desc: String)