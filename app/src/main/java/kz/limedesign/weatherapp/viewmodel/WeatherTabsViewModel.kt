package kz.limedesign.weatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.limedesign.weatherapp.model.WeatherResponse
import kz.limedesign.weatherapp.network.*
import retrofit2.Response

class WeatherTabsViewModel : ViewModel() {

    var api: Api = NetworkService.retrofitService()
    val liveData = MutableLiveData<Result<WeatherResponse>>()

    fun <T> requestWithLiveData(
        liveData: MutableLiveData<Result<T>>,
        request: suspend () -> Response<T>
    ) {
        liveData.postValue(Result.loading())

        this.viewModelScope.launch(Dispatchers.IO) {
            try {

                val response = request.invoke()
                liveData.postValue(
                    Result.success(response.body())
                )

            } catch (e: Exception) {
                e.printStackTrace()
                liveData.postValue(
                    Result.error(Error(e))
                )
            }
        }
    }

    fun getWeather(latlng: String) {
        requestWithLiveData(liveData) {
            api.getWeather(latlng)
        }
    }


}