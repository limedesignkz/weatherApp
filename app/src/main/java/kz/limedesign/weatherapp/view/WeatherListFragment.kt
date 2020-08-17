package kz.limedesign.weatherapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_weather_list.*
import kz.limedesign.weatherapp.R
import kz.limedesign.weatherapp.view.adapter.WeatherListAdapter
import kz.limedesign.weatherapp.viewmodel.WeatherListViewModel

@AndroidEntryPoint
class WeatherListFragment : Fragment() {

    private val viewModel: WeatherListViewModel by viewModels()

    companion object {
        private val ARG_PAGE_NUMBER = "page_number"

        fun newInstance(position: Int): WeatherListFragment {
            val fragment = WeatherListFragment()
            fragment.arguments = bundleOf(
                ARG_PAGE_NUMBER to position
            )
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = WeatherListAdapter()
        recycler_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycler_view.adapter = adapter

        viewModel.loadData()

        viewModel.liveData.observe(viewLifecycleOwner, Observer {list ->
            adapter.submitList(list)
        })
    }
}