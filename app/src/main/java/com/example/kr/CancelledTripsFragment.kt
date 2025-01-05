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

class CancelledTripsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_trips_list, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = TripsAdapter(
            trips = getCancelledTrips(),
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

    private fun getCancelledTrips(): List<Trip> {
        return listOf(
            Trip("Отмененный Отель 1", "Сочи, Россия", "10 - 15 июля 2023 г.", "30 000 руб."),
            Trip("Отмененный Отель 2", "Казань, Россия", "5 - 10 августа 2023 г.", "25 000 руб.")
        )
    }
}
