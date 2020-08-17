package kz.limedesign.weatherapp.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.limedesign.weatherapp.model.DailyWeather
import kz.limedesign.weatherapp.repository.DailyWeatherRepository

class WeatherListViewModel @ViewModelInject constructor(private val dailyWeatherRepository: DailyWeatherRepository) :
ViewModel(), LifecycleObserver {

    val liveData = MutableLiveData<List<DailyWeather>>()
    private var isLoading = false

    fun loadData() {
        isLoading = true

        this.viewModelScope.launch(Dispatchers.IO) {
            dailyWeatherRepository.getWeatherList(object :
                DailyWeatherRepository.OnRepositoryReadyCallback {
                override fun onDataReady(data: List<DailyWeather>) {
                    isLoading = false
                    liveData.postValue(data)
                }
            })
        }
    }
}