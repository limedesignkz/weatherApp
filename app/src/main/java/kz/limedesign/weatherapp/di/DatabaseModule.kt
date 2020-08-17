package kz.limedesign.weatherapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kz.limedesign.weatherapp.db.AppDatabase
import kz.limedesign.weatherapp.db.DailyWeatherDao
import kz.limedesign.weatherapp.repository.DailyWeatherLocalDataSource
import kz.limedesign.weatherapp.repository.DailyWeatherRemoteDataSource
import kz.limedesign.weatherapp.repository.DailyWeatherRepository

@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideDailyWeatherDao(@ApplicationContext appContext: Context) : DailyWeatherDao {
        return AppDatabase.getInstance(appContext).dailyWeatherItemDao()
    }

    @Provides
    fun provideDailyWeatherLocalDataSource(dao: DailyWeatherDao) = DailyWeatherLocalDataSource(dao)

    @Provides
    fun provideDailyWeatherRemoteDataSource() = DailyWeatherRemoteDataSource()

    @Provides
    fun provideDailyWeatherRepository(local: DailyWeatherLocalDataSource, remote: DailyWeatherRemoteDataSource) =
        DailyWeatherRepository(local, remote)

}