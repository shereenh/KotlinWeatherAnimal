package shereen.weather.animal.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import shereen.weather.animal.model.WConstants
import shereen.weather.animal.model.room.dao.CityDao
import shereen.weather.animal.model.room.entity.CityEntity

@Database(entities = arrayOf(CityEntity::class), version = 1)
abstract class WDatabase: RoomDatabase() {

    abstract fun cityDao(): CityDao

    companion object {
        private var INSTANCE: WDatabase? = null

        fun getInstance(context: Context): WDatabase? {
            if(INSTANCE == null){
                synchronized(WDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        WDatabase::class.java,
                        WConstants.DATABASE_NAME)
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}