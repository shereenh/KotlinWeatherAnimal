package shereen.weather.animal.model.room.entity

import androidx.annotation.NonNull
import androidx.room.*
import shereen.weather.animal.R
import shereen.weather.animal.model.WConstants
import shereen.weather.animal.model.room.DayConverter

@Entity(tableName = WConstants.CITY_TABLE, primaryKeys = [WConstants.CITY_NAME, WConstants.CITY_STATE, WConstants.CITY_COUNTRY])
class CityEntity(@ColumnInfo(name = WConstants.CITY_NAME) var name: String,
                 @ColumnInfo(name = WConstants.CITY_STATE) var state: String,
                 @ColumnInfo(name = WConstants.CITY_COUNTRY) var country: String,
                 @ColumnInfo(name = WConstants.CITY_TEMP) var temp: Double,
                 @ColumnInfo(name = WConstants.CITY_MIN) var temp_min: Double,
                 @ColumnInfo(name = WConstants.CITY_MAX) var temp_max: Double,
                 @ColumnInfo(name = WConstants.CITY_DESCRIPTION) var description: String,
                 @ColumnInfo(name = WConstants.CITY_LIST)
                 @TypeConverters(DayConverter::class)
                 var day: MutableList<Day> = mutableListOf()) {
    @Ignore
    constructor(name: String, state: String, country: String): this(name, state, country, 0.0, 0.0,0.0, "")
//    @Ignore
//    constructor(name: String, state: String, country: String, temp: String, temp_min: String, temp_max: String, description: String, icon: Int) :
//            this(name, state,  country, temp, temp_max, temp_min, description, icon)
}
