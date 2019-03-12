package shereen.weather.animal.model.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import shereen.weather.animal.R
import shereen.weather.animal.model.WConstants

@Entity(tableName = WConstants.CITY_TABLE)
class CityEntity(@PrimaryKey(autoGenerate = true) var id: Long? = null,
                 @ColumnInfo(name = WConstants.CITY_NAME) var name: String,
                 @ColumnInfo(name = WConstants.CITY_TEMP) var temp: Double = 0.0,
                 @ColumnInfo(name = WConstants.CITY_ICON) var icon: Int = R.drawable.sunny)