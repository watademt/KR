package com.example.kr.employee

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kr.LoginActivity
import com.example.kr.booking.Booking
import com.example.kr.R
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

//Обработка нажатия кнопок на странице сотрудника
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
                hotelName = "Club com.example.kr.Hotel.Hotel Anjeliq",
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


        findViewById<View>(R.id.buttonExit).setOnClickListener {
            Firebase.auth.signOut()
//
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Удаляем историю переходов
            startActivity(intent)
        }
    }

    private fun updateBookingStatus(bookingId: String, newStatus: String) {
        // Находим бронь и обновляем ее статус
        bookings.find { it.id == bookingId }?.status = newStatus

        // Обновляем адаптер только с активными бронями
        val activeBookings = bookings.filter { it.status == "active" }
        adapter.updateBookings(activeBookings)
    }
}