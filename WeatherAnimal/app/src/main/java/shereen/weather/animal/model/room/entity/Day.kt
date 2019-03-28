package shereen.weather.animal.model.room.entity

class Day(
    var day: String,
    var temp: Double,
    var hourList: MutableList<Hour>)