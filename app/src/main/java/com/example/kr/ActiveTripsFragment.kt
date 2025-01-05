package com.example.kr

import TripsAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ActiveTripsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_trips_list, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = TripsAdapter(
            trips = getActiveTrips(),
            onLeaveReview = { trip ->
                // Логика для оставления отзыва
                Toast.makeText(requireContext(), "Оставить отзыв для: ${trip.name}", Toast.LENGTH_SHORT).show()
            },
            onRepeatBooking = { trip ->
                // Логика для повторного бронирования
                Toast.makeText(requireContext(), "Повторить бронирование для: ${trip.name}", Toast.LENGTH_SHORT).show()
            }
        )
        return view
    }

    private fun getActiveTrips(): List<Trip> {
        return listOf(
            Trip("Активный Отель 1", "Москва, Россия", "10 - 15 мая 2023 г.", "15 000 руб."),
            Trip("Активный Отель 2", "Санкт-Петербург, Россия", "20 - 25 июня 2023 г.", "20 000 руб.")
        )
    }
}
