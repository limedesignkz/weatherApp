package kz.limedesign.weatherapp.repository

import kz.limedesign.weatherapp.db.DailyWeatherDao
import kz.limedesign.weatherapp.model.DailyWeather
import javax.inject.Inject

class DailyWeatherLocalDataSource @Inject constructor(private val dao: DailyWeatherDao) : BaseDataSource<DailyWeather>() {

    override suspend fun getData(callback: OnReadyCallback<DailyWeather>) {
        callback.onDataReady(dao.fetch())
    }

    fun insert(item: DailyWeather) {
        dao.insert(item)
    }

    fun saveData(list: List<DailyWeather>){
        for (item in list) {
            dao.insert(item)
        }
    }

}
