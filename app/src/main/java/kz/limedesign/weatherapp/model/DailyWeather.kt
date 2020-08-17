package kz.limedesign.weatherapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DailyWeather(
    var temperature: Float?,
    @PrimaryKey var time: Int?
)