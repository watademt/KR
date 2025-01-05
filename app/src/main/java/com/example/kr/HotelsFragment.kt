package com.example.kr

import Hotel
import HotelAdapter
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HotelsFragment : Fragment(R.layout.fragment_hotels) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val hotels = listOf(
            Hotel("Rios Beach", "Отель для экономного отдыха", "167 225 руб.", R.drawable.hotel_image),
            Hotel("Club Hotel Anjeliq", "Отель с бассейнами и горками", "198 285 руб.", R.drawable.hotel_image),
            Hotel("Грейс Аква Вилла", "Отель с первой линией на берегу", "160 417 руб.", R.drawable.hotel_image)
        )

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = HotelAdapter(hotels)
    }
}
