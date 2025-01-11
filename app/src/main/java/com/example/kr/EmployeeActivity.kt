package com.example.kr

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EmployeeActivity : AppCompatActivity() {

    private lateinit var adapter: BookingAdapter
    private lateinit var bookings: MutableList<Booking>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewBookings)

        // Пример данных
        bookings = mutableListOf(
            Booking(
                id = "1",
                hotelName = "Rios Beach",
                userName = "Иванов Иван",
                startDate = "10.01.2025",
                endDate = "15.01.2025",
                price = "150 000 руб.",
                roomType = "Люкс",
                bedDetails = "2 кровати, Кинг-сайз",
                hotelImageRes = R.drawable.hotel_image,
                status = "active"
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
                hotelImageRes = R.drawable.hotel_image,
                status = "cancelled" // Эта бронь будет исключена
            )
        )

        // Фильтруем только активные брони
        val activeBookings = bookings.filter { it.status == "active" }.toMutableList()

        adapter = BookingAdapter(activeBookings) { bookingId, newStatus ->
            // Обновляем статус брони
            updateBookingStatus(bookingId, newStatus)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun updateBookingStatus(bookingId: String, newStatus: String) {
        // Находим бронь и обновляем ее статус
        bookings.find { it.id == bookingId }?.status = newStatus

        // Обновляем адаптер только с активными бронями
        val activeBookings = bookings.filter { it.status == "active" }
        adapter.updateBookings(activeBookings)
    }
}

