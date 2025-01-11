package com.example.kr.Trip

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.kr.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

//Переход между Активными, Прошлыми и Отмененными поездками
class TripsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_trips)

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)

        // Настройка ViewPager2 с адаптером
        val adapter = TripsPagerAdapter(this)
        viewPager.adapter = adapter

        // Синхронизация TabLayout и ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Активные"
                1 -> "Прошлые"
                else -> "Отмененные"
            }
        }.attach()
    }
}
