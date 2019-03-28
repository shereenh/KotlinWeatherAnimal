package shereen.weather.animal.viewmodel.entity

import shereen.weather.animal.model.room.entity.Day

class DetailView(var cityView: CityView,
                 var day: List<DayView> = mutableListOf()) {
}