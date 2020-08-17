package kz.limedesign.weatherapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_weather.*
import kz.limedesign.weatherapp.R
import kz.limedesign.weatherapp.model.WeatherResponse
import kz.limedesign.weatherapp.network.Error
import kz.limedesign.weatherapp.network.Status
import kz.limedesign.weatherapp.utils.Logger
import kz.limedesign.weatherapp.utils.Strings
import kz.limedesign.weatherapp.viewmodel.WeatherTabsViewModel

@AndroidEntryPoint
class WeatherTabsFragment : Fragment() {

    private val viewModel: WeatherTabsViewModel by viewModels()

    companion object {
        fun newInstance() = WeatherTabsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        pager.adapter = WeatherPagerAdapter(this)

        TabLayoutMediator(tabs, pager) { tab, position ->
            tab.text = when (position) {
                0 -> Strings.get(R.string.city_spb)
                1 -> Strings.get(R.string.city_msk)
                else -> throw IllegalArgumentException("Argument out of range")
            }
        }.attach()

        viewModel.liveData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> showProgress()
                Status.SUCCESS -> update(it.data)
                Status.ERROR -> handleError(it.error)
            }
        })

        viewModel.getWeather("56,70")

    }

    fun showProgress() {
        Logger.d("loading...")
    }

    fun update(data: WeatherResponse?) {
        Logger.d("success: " + data?.lat)
    }

    fun handleError(error: Error?) {
        Logger.d("error: $error")
    }

    private inner class WeatherPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun createFragment(position: Int): Fragment = WeatherListFragment.newInstance(position)
        override fun getItemCount(): Int = 2
    }

}