package kz.limedesign.weatherapp.repository

abstract class BaseDataSource<T> {

    abstract suspend fun getData(callback: OnReadyCallback<T>)

    interface OnReadyCallback<T> {
        fun onDataReady(data: List<T>)
    }
}