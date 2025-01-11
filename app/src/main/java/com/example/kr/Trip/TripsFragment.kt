package com.example.kr.Trip

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.kr.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class TripsFragment : Fragment(R.layout.fragment_trips) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Инициализация TabLayout и ViewPager2
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)

        // Настройка адаптера для ViewPager2
        val adapter = TripsPagerAdapter(requireActivity())
        viewPager.adapter = adapter

        // Связываем TabLayout с ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Активные" // Название первой вкладки
                1 -> "Прошлые"  // Название второй вкладки
                else -> "Отмененные" // Название третьей вкладки
            }
        }.attach()
    }
}
