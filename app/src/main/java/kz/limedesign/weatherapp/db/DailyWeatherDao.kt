package kz.limedesign.weatherapp.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kz.limedesign.weatherapp.model.DailyWeather

@Dao
interface DailyWeatherDao {
    @Query("SELECT * FROM dailyweather")
    fun fetch() : List<DailyWeather>

    /*@Query("SELECT * FROM dailyweather WHERE id = :id")
    fun getById(id: Long): DailyWeather?*/

    @Insert
    fun insert(dailyWeather: DailyWeather?)

    /*@Update
    fun update(dailyWeather: DailyWeather?)*/

    @Delete
    fun delete(dailyWeather: DailyWeather?)
}