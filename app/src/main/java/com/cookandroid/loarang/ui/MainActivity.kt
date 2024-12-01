package com.cookandroid.loarang.ui

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.fragment.app.Fragment
import com.cookandroid.loarang.ui.schedule.ScheduleFragment
import com.cookandroid.loarang.ui.character.CharacterFragment
import com.cookandroid.loarang.ui.homework.HomeworkFragment
import com.cookandroid.loarang.ui.info.InfoFragment
import com.cookandroid.loarang.R
import com.cookandroid.loarang.ui.setting.SettingFragment
import com.cookandroid.loarang.base.BaseActivity
import com.cookandroid.loarang.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var currentFragment: String = CharacterFragment.TAG

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initNavigationBar()

        // 화면 재구성 될 경우 현재 fragment 유지
        if (savedInstanceState != null) {
            currentFragment = savedInstanceState.getString("currentFragment").toString()
            when (currentFragment) {
                CharacterFragment.TAG -> showFragment(
                    CharacterFragment(), CharacterFragment.TAG)
                HomeworkFragment.TAG -> showFragment(
                    HomeworkFragment(), HomeworkFragment.TAG)
                ScheduleFragment.TAG -> showFragment(
                    ScheduleFragment(), ScheduleFragment.TAG)
                InfoFragment.TAG -> showFragment(InfoFragment(), InfoFragment.TAG)
                SettingFragment.TAG -> showFragment(
                    SettingFragment(), SettingFragment.TAG)
            }
        } else showFragment(CharacterFragment(), CharacterFragment.TAG)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("currentFragment", currentFragment)
    }

    private fun initNavigationBar() {
        binding.menuBottom.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.tab_character -> {
                    showFragment(CharacterFragment.newInstance(), CharacterFragment.TAG)
                    currentFragment = CharacterFragment.TAG
                    vibrateShort()
                    true
                }

                R.id.tab_homework -> {
                    showFragment(HomeworkFragment.newInstance(), HomeworkFragment.TAG)
                    currentFragment = HomeworkFragment.TAG
                    vibrateShort()
                    true
                }

                R.id.tab_calender -> {
                    showFragment(ScheduleFragment.newInstance(), ScheduleFragment.TAG)
                    currentFragment = ScheduleFragment.TAG
                    vibrateShort()
                    true
                }

                R.id.tab_info -> {
                    showFragment(InfoFragment.newInstance(), InfoFragment.TAG)
                    currentFragment = InfoFragment.TAG
                    vibrateShort()
                    true
                }

                R.id.tab_setting -> {
                    showFragment(SettingFragment.newInstance(), SettingFragment.TAG)
                    currentFragment = SettingFragment.TAG
                    vibrateShort()
                    true
                }

                else -> false
            }
        }
    }

    private fun showFragment(fragment: Fragment, tag: String) {
        val findFragment = supportFragmentManager.findFragmentByTag(tag)

        supportFragmentManager.fragments.forEach { fm ->
            if (fm != fragment) {
                supportFragmentManager.beginTransaction().hide(fm).commitAllowingStateLoss()
            }
        }

        findFragment?.let {
            supportFragmentManager.beginTransaction().show(it).commitAllowingStateLoss()
        } ?: kotlin.run {
            supportFragmentManager.beginTransaction()
                .add(R.id.frameLayout, fragment, tag)
                .commit()
        }
    }

    private fun vibrateShort() {
        // 진동
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(10, 100))
        } else {
            vibrator.vibrate(10)
        }
    }
}