package com.cookandroid.loarang

import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    var home_ly: LinearLayout? = null
    var bottomNavigationView: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        home_ly = findViewById(R.id.home_ly)
        bottomNavigationView = findViewById(R.id.menu_bottom)
        //bottomNavigationView.setOnNavigationItemSelectedListener(TabSelectedListener())
        //bottomNavigationView.setSelectedItemId(R.id.tab_character)
    }

    internal inner class TabSelectedListener :
        BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
            val itemId = menuItem.itemId
            if (itemId == R.id.tab_character) {
                supportFragmentManager.beginTransaction().replace(R.id.home_ly, CharacterFragment())
                    .commit()
                return true
            } else if (itemId == R.id.tab_homework) {
                supportFragmentManager.beginTransaction().replace(R.id.home_ly, HomeworkFragment())
                    .commit()
                return true
            } else if (itemId == R.id.tab_calender) {
                supportFragmentManager.beginTransaction().replace(R.id.home_ly, CalenderFragment())
                    .commit()
                return true
            } else if (itemId == R.id.tab_info) {
                supportFragmentManager.beginTransaction().replace(R.id.home_ly, InfoFragment())
                    .commit()
                return true
            } else if (itemId == R.id.tab_setting) {
                supportFragmentManager.beginTransaction().replace(R.id.home_ly, SettingFragment())
                    .commit()
                return true
            }
            return false
        }
    }
}