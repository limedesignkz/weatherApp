package kz.limedesign.weatherapp.repository

import kz.limedesign.weatherapp.model.DailyWeather
import javax.inject.Inject


class DailyWeatherRepository @Inject constructor(
    private val localDataSource: DailyWeatherLocalDataSource,
    private val remoteDataSource: DailyWeatherRemoteDataSource
) {

    suspend fun getWeatherList(onRepositoryReadyCallback: OnRepositoryReadyCallback) {
        remoteDataSource.getData(object : BaseDataSource.OnReadyCallback<DailyWeather>{
            override fun onDataReady(data: List<DailyWeather>) {
                localDataSource.saveData(data)
                onRepositoryReadyCallback.onDataReady(data)
            }
        })
        localDataSource.getData(object: BaseDataSource.OnReadyCallback<DailyWeather>{
            override fun onDataReady(data: List<DailyWeather>) {
                //TODO("Not yet implemented")
            }
        })
    }

    interface OnRepositoryReadyCallback {
        fun onDataReady(data : List<DailyWeather>)
    }
}

