package kz.limedesign.weatherapp.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kz.limedesign.weatherapp.view.MapsFragment
import kz.limedesign.weatherapp.view.WeatherTabsFragment

object Navigator {

    enum class Links(val fragment: Fragment) {
        MAP ( WeatherTabsFragment.newInstance() ),
        LIST ( MapsFragment.newInstance() )
    }

    fun navigate(context: AppCompatActivity, viewGroup: Int, link: Links) {

        clearAll(context)

        val fragmentManager = context.supportFragmentManager
        val tag = link.toString()

        val existingFragment = fragmentManager.findFragmentByTag(tag)

        when (existingFragment) {
            null -> {
                fragmentManager.beginTransaction()
                    .add(viewGroup, link.fragment, tag)
                    .commit()
            }
            else -> fragmentManager.beginTransaction()
                .replace(viewGroup, link.fragment, tag)
                .commit()
        }
    }

    fun clearAll(context: AppCompatActivity) {
        val fragmentManager = context.supportFragmentManager
        for (fragment in fragmentManager.fragments) {
            if (fragment != null) {
                fragmentManager.beginTransaction().remove(fragment).commit()
            }
        }
    }

}

