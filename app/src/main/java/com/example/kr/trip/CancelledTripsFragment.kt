package com.example.kr.trip

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
import com.example.kr.R

//Отмененные поездки
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
                val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_review, null)
                val dialog = AlertDialog.Builder(context)
                    .setView(dialogView)
                    .create()

                val ratingBar = dialogView.findViewById<RatingBar>(R.id.review_rating)
                val commentInput = dialogView.findViewById<EditText>(R.id.review_comment)
                val submitButton = dialogView.findViewById<Button>(R.id.submit_button)
                val cancelButton = dialogView.findViewById<Button>(R.id.cancel_button)

                // Обработка нажатия на кнопку "Отмена"
                cancelButton.setOnClickListener {
                    dialog.dismiss()
                }

                // Добавление слушателя для изменений в RatingBar
                ratingBar.setOnRatingBarChangeListener { _, newRating, _ ->
                    Toast.makeText(context, "Вы выбрали рейтинг: $newRating", Toast.LENGTH_SHORT).show()
                }

                // Обработка нажатия на кнопку "Отправить"
                submitButton.setOnClickListener {
                    val rating = ratingBar.rating
                    val comment = commentInput.text.toString()

                    if (rating > 0) {
                        Toast.makeText(context, "Спасибо за отзыв с оценкой: $rating", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    } else {
                        Toast.makeText(context, "Пожалуйста, поставьте оценку", Toast.LENGTH_SHORT).show()
                    }
                }

                dialog.show()
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
