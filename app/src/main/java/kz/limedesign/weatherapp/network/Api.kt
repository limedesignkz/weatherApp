package kz.limedesign.weatherapp.network

import kz.limedesign.weatherapp.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("forecast/3e7e519ea86c8e3fcf67c0f4870513d7/{latlng}")
    suspend fun getWeather(@Path("latlng") latlng: String): Response<WeatherResponse>

}