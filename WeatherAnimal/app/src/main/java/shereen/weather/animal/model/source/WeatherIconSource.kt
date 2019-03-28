package shereen.weather.animal.model.source

import shereen.weather.animal.R

class WeatherIconSource {

    companion object {

        const val clear = R.drawable.clear
        const val clouds = R.drawable.clouds
        const val sun_couldy = R.drawable.cloudy
        const val drizzle = R.drawable.drizzle
        const val fog = R.drawable.fog
        const val lightning = R.drawable.lightning
        const val overcast = R.drawable.overcast
        const val rain = R.drawable.rain
        const val rainbow = R.drawable.rainbow
        const val snow = R.drawable.snowy
        const val storm = R.drawable.storm
        const val sun = R.drawable.sunny
        const val thunder = R.drawable.thunder
        const val tornado = R.drawable.tornado

        fun getWeaIcon(description: String):Int{

            return when {
                description.contains("sun") -> sun
                description.contains("cloud") -> clouds
                description.contains("drizzle") -> drizzle
                description.contains("fog") -> fog
                description.contains("clear") -> clear
                description.contains("thunder") -> thunder
                description.contains("snow") -> snow
                description.contains("rain") -> rain
                description.contains("lightning") -> lightning
                description.contains("overcast") -> overcast
                description.contains("storm") -> storm
                description.contains("mist") -> fog
                description.contains("haze") -> fog
                else -> rainbow
            }
        }
    }
}