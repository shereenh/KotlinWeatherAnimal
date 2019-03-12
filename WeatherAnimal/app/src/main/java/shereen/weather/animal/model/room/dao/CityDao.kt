package shereen.weather.animal.model.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Ignore
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import shereen.weather.animal.model.WConstants
import shereen.weather.animal.model.room.entity.CityEntity

@Dao
interface CityDao {

    @Insert(onConflict = IGNORE)
    fun insertCity(city: CityEntity)

    @Query("SELECT * FROM " + WConstants.CITY_TABLE)
    fun getAllCities(): LiveData<List<CityEntity>>

    @Query("DELETE FROM " + WConstants.CITY_TABLE)
    fun deleteAllCities()

}