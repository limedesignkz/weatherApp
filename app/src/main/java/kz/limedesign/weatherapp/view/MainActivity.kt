package kz.limedesign.weatherapp.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import kz.limedesign.weatherapp.R
import kz.limedesign.weatherapp.utils.Navigator


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Navigator.navigate(this, R.id.container, Navigator.Links.LIST)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_map -> {
                Navigator.navigate(this, R.id.container, Navigator.Links.MAP)
                return true
            }
            R.id.item_list -> {
                Navigator.navigate(this, R.id.container, Navigator.Links.LIST)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}