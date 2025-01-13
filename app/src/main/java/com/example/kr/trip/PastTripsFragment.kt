package com.example.kr.trip

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kr.R
import com.example.kr.booking.BookingActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PastTripsFragment : Fragment(R.layout.fragment_trips_list) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        loadPastTrips(recyclerView)
    }

    private fun loadPastTrips(recyclerView: RecyclerView) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        var status="0"
        FirebaseFirestore.getInstance()
            .collection("bookings")
            .whereEqualTo("clientUID", userId)
            .get()
            .addOnSuccessListener { result ->
                val trips = result.mapNotNull { document ->
                    val endDate = document.getString("endDate") ?: ""
                    val originalPrice = document.getDouble("hotelPrice") ?: 0.0
                    val nights = document.getDouble("nights") ?: 0.0
                    val modifiedPrice = originalPrice / nights
                    if (endDate < currentDate) {
                        Trip(
                            status = "2",
                            name = document.getString("hotelName") ?: "",
                            location = document.getString("hotelLocation") ?: "",
                            dates = "${document.getString("startDate")} - $endDate",
                            price = document.getDouble("hotelPrice")?.toString() ?: "",
                            pricePerNight = modifiedPrice.toString(),
                            description = document.getString("hotelDescription") ?: "",
                            imageResource = document.getString("hotelImageRes") ?: "default_image"
                        )
                    } else null
                }
                recyclerView.adapter = TripsAdapter(trips, requireContext(), ::onLeaveReview, ::onRepeatBooking)
            }
            .addOnFailureListener {
                Toast.makeText(context, "Ошибка загрузки прошлых поездок", Toast.LENGTH_SHORT).show()
            }
    }

    private fun onLeaveReview(trip: Trip) {
        // Логика для оставления отзыва

    }

    private fun onRepeatBooking(trip: Trip) {
        // Переход в BookingActivity с передачей данных о поездке
        val intent = Intent(requireContext(), BookingActivity::class.java).apply {
            putExtra("hotel_name", trip.name)
            putExtra("hotel_description", trip.description)
            putExtra("hotel_price", trip.pricePerNight)
            putExtra("hotel_image_resource", trip.imageResource)
            putExtra("hotel_location", trip.location)
        }
        startActivity(intent)
    }
}