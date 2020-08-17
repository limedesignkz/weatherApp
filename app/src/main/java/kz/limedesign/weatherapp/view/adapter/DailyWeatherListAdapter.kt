package kz.limedesign.weatherapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kz.limedesign.weatherapp.R
import kz.limedesign.weatherapp.model.DailyWeather

class WeatherListAdapter() : ListAdapter<DailyWeather, WeatherListAdapter.ItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_weather_list_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) = holder.bindTo(getItem(position))


    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private lateinit var dailyWeather: DailyWeather
        private val temperatureTextView = itemView.findViewById<TextView>(R.id.weather_temperature)
        private val summaryTextView = itemView.findViewById<TextView>(R.id.weather_summary)

        fun bindTo(dailyWeather: DailyWeather) {
            this.dailyWeather = dailyWeather
            temperatureTextView.text = dailyWeather.temperature.toString()
            summaryTextView.text = dailyWeather.time.toString()
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<DailyWeather>() {
        override fun areItemsTheSame(oldItem: DailyWeather, newItem: DailyWeather): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: DailyWeather, newItem: DailyWeather): Boolean = oldItem == newItem

    }
}
