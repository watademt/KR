package com.example.kr

import Hotel
import HotelAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SearchFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Список отелей
        val hotels = listOf(
            Hotel("Rios Beach", "Отель для экономного отдыха", "167 225 руб.", R.drawable.hotel_image),
            Hotel("Club Hotel Anjeliq", "Отель с бассейнами и горками", "198 285 руб.", R.drawable.hotel_image),
            Hotel("Грейс Аква Вилла", "Отель с первой линией на берегу", "160 417 руб.", R.drawable.hotel_image)
        )

        // Настройка RecyclerView
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = HotelAdapter(hotels)
    }
}
