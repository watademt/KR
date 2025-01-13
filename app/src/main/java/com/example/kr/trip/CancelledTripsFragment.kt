package com.example.kr.trip

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kr.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CancelledTripsFragment : Fragment(R.layout.fragment_trips_list) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        loadCancelledTrips(recyclerView)
    }

    private fun loadCancelledTrips(recyclerView: RecyclerView) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        FirebaseFirestore.getInstance()
            .collection("bookings")
            .whereEqualTo("clientUID", userId)
            .whereEqualTo("status", "2")
            .get()
            .addOnSuccessListener { result ->
                val trips = result.mapNotNull { document ->
                    Trip(
                        name = document.getString("hotelName") ?: "",
                        location = document.getString("hotelLocation") ?: "",
                        dates = "${document.getString("startDate")} - ${document.getString("endDate")}",
                        price = document.getDouble("hotelPrice")?.toString() ?: "",
                        description = document.getString("hotelDescription") ?: ""
                    )
                }
                recyclerView.adapter = TripsAdapter(trips, ::onLeaveReview, ::onRepeatBooking)
            }
            .addOnFailureListener {
                Toast.makeText(context, "Ошибка загрузки отмененных поездок", Toast.LENGTH_SHORT).show()
            }
    }

    private fun onLeaveReview(trip: Trip) {
        // Логика для оставления отзыва
    }

    private fun onRepeatBooking(trip: Trip) {
        // Логика для повторного бронирования
    }
}