package com.cookandroid.loarang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    LinearLayout home_ly;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        home_ly = findViewById(R.id.home_ly);
        bottomNavigationView = findViewById(R.id.menu_bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(new TabSelectedListener());
        bottomNavigationView.setSelectedItemId(R.id.tab_character);
    }

    class TabSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.tab_character: {
                    getSupportFragmentManager().beginTransaction() .replace(R.id.home_ly, new CharacterFragment()) .commit();
                    return true;
                }
                case R.id.tab_homework: {
                    getSupportFragmentManager().beginTransaction() .replace(R.id.home_ly, new HomeworkFragment()) .commit();
                    return true;
                }
                case R.id.tab_calender: {
                    getSupportFragmentManager().beginTransaction() .replace(R.id.home_ly, new CalenderFragment()) .commit();
                    return true;
                }
                case R.id.tab_info: {
                    getSupportFragmentManager().beginTransaction() .replace(R.id.home_ly, new InfoFragment()) .commit();
                    return true;
                }
                case R.id.tab_setting: {
                    getSupportFragmentManager().beginTransaction() .replace(R.id.home_ly, new SettingFragment()) .commit();
                    return true;
                }
            }
            return false;
        }
    }
}