package shereen.weather.animal.viewmodel.entity

import shereen.weather.animal.model.room.entity.Hour

class DayView(var day: String,
              var icon: Int,
              var temp: String,
              var hourList: MutableList<HourView>)