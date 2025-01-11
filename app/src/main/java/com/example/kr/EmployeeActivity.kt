package com.example.kr

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EmployeeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewBookings)

        // Пример данных
        val bookings = listOf(
            Booking(
                id = "1",
                hotelName = "Rios Beach",
                userName = "Иванов Иван",
                startDate = "10.01.2025",
                endDate = "15.01.2025",
                price = "150 000 руб.",
                roomType = "Люкс",
                bedDetails = "2 кровати, Кинг-сайз",
                hotelImageRes = R.drawable.hotel_image
            ),
            Booking(
                id = "2",
                hotelName = "Club Hotel Anjeliq",
                userName = "Петров Петр",
                startDate = "12.01.2025",
                endDate = "18.01.2025",
                price = "200 000 руб.",
                roomType = "Одноместный",
                bedDetails = "1 кровать, Двуспальная",
                hotelImageRes = R.drawable.hotel_image
            )
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = BookingAdapter(bookings)
    }
}
