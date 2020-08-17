package kz.limedesign.weatherapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kz.limedesign.weatherapp.utils.LocationLiveData

class MapsViewModel(application: Application) : AndroidViewModel(application)  {
    val locationData =
        LocationLiveData(application)
}