package kz.limedesign.weatherapp.repository

import kz.limedesign.weatherapp.model.DailyWeather
import javax.inject.Inject

class DailyWeatherRemoteDataSource @Inject constructor() : BaseDataSource<DailyWeather>() {
    override suspend fun getData(callback: OnReadyCallback<DailyWeather>) {
        /*var list = ArrayList<DailyWeather>()
        list.add(DailyWeather(10.5f, 1))
        list.add(DailyWeather(11.5f, 2))
        list.add(DailyWeather(12.5f, 3))

        callback.onDataReady(list)*/

        //Handler().postDelayed({ callback.onDataReady(list) }, 2000)
    }
}

