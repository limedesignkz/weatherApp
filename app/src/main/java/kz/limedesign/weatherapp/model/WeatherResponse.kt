package kz.limedesign.weatherapp.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("latitude")
    var lat: Float?,
    @SerializedName("longitude")
    var lng: Float?,
    @SerializedName("currently")
    var currently: CurrentlyWeather?,
    @SerializedName("daily")
    var daily: DailyWeatherList?
) {
    data class CurrentlyWeather(
        @SerializedName("summary")
        var summary: String?,
        @SerializedName("temperature")
        var temperature: Float?
    )

    data class DailyWeatherList(
        @SerializedName("data")
        var items: List<DailyWeather>
    )

    data class DailyWeather(
        @SerializedName("time")
        var time: Int?,
        @SerializedName("temperatureMax")
        var temperature: Float?
    )
}