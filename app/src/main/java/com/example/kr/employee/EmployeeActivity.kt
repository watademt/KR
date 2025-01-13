package com.example.kr.employee

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kr.LoginActivity
import com.example.kr.R
import com.example.kr.booking.Booking
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore

class EmployeeActivity : AppCompatActivity() {

    private lateinit var adapter: BookingAdapter
    private lateinit var bookings: MutableList<Booking>
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewBookings)
        recyclerView.layoutManager = LinearLayoutManager(this)

        bookings = mutableListOf()

        // Инициализация адаптера
        adapter = BookingAdapter(bookings) { bookingId, newStatus ->
            //updateBookingStatus(bookingId, newStatus)
        }
        recyclerView.adapter = adapter

        // Загрузка бронирований из Firestore
        loadBookingsFromFirestore()

        findViewById<View>(R.id.buttonExit).setOnClickListener {
            Firebase.auth.signOut()
            // Переход на экран входа
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Удаляем историю переходов
            startActivity(intent)
        }
    }

    private fun loadBookingsFromFirestore() {
        firestore.collection("bookings")
            .get()
            .addOnSuccessListener { result ->
                val activeBookings = mutableListOf<Booking>()
                for (document in result) {
                    val status = document.getString("status") ?: "unknown"
                    if (status == "0") { // Проверка статуса для активных
                        val booking = Booking(
                            id = document.id,
                            hotelName = document.getString("hotelName") ?: "",
                            userName = document.getString("clientName") ?: "",
                            clientPhone = document.getString("clientPhone") ?: "", // Номер телефона
                            startDate = document.getString("startDate") ?: "",
                            endDate = document.getString("endDate") ?: "",
                            price = document.get("hotelPrice").toString(),
                            roomType = document.getString("roomType") ?: "",
                            bedDetails = "${document.getString("numberOfBeds")} кровати, ${document.getString("bedType")}",
                            hotelImageRes = document.getString("hotelImageRes") ?: "default_image",
                            status = "active"
                        )
                        activeBookings.add(booking)
                    }
                }
                adapter.updateBookings(activeBookings)
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }



//    private fun updateBookingStatus(bookingId: String, newStatus: String) {
        // Обновляем статус бронирования в Firestore
//        Toast.makeText(this, bookingId, Toast.LENGTH_SHORT).show()
//        firestore.collection("bookings").document(bookingId)
//            .update("status", newStatus)
//            .addOnSuccessListener {
                // После обновления загружаем актуальный список
//                loadBookingsFromFirestore()
//            }
//            .addOnFailureListener { e ->
//                e.printStackTrace()
//            }
//    }
}
