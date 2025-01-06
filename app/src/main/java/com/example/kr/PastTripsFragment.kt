package com.example.kr

import TripsAdapter
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PastTripsFragment : Fragment() {
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_trips_list, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = TripsAdapter(
            trips = getPastTrips(),
            onLeaveReview = { trip ->
                val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_review, null)
                val dialog = AlertDialog.Builder(context)
                    .setView(dialogView)
                    .create()

                val ratingBar = dialogView.findViewById<RatingBar>(R.id.review_rating)
                val commentInput = dialogView.findViewById<EditText>(R.id.review_comment)
                val submitButton = dialogView.findViewById<Button>(R.id.submit_button)
                val cancelButton = dialogView.findViewById<Button>(R.id.cancel_button)

                cancelButton.setOnClickListener {
                    dialog.dismiss()
                }

                submitButton.setOnClickListener {
                    val rating = ratingBar.rating
                    val comment = commentInput.text.toString()
                    if (rating > 0) {
                        Toast.makeText(context, "Спасибо за отзыв!", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    } else {
                        Toast.makeText(context, "Пожалуйста, поставьте оценку", Toast.LENGTH_SHORT).show()
                    }
                }

                dialog.show()
            }
            ,
            onRepeatBooking = { trip ->
                // Логика для повторного бронирования
                Toast.makeText(requireContext(), "Повторить бронирование для: ${trip.name}", Toast.LENGTH_SHORT).show()
            }
        )
        return view
    }

    private fun getPastTrips(): List<Trip> {
        return listOf(
            Trip("Грейс Аква Вилла 4*", "Абхазия, Сухум", "24 - 31 июля 2023 г.", "10 349 руб."),
            Trip("Rios Beach (Intersport Hotel) 4*", "Турция, Кемер", "29 - 5 июля 2022 г.", "120 389 руб.")
        )
    }
}
