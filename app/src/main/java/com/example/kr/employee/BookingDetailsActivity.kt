package com.example.kr.employee

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.kr.R
import com.google.firebase.firestore.FirebaseFirestore

class BookingDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_details)

        val firestore = FirebaseFirestore.getInstance()

        val hotelImage = findViewById<ImageView>(R.id.hotelImage)
        val hotelName = findViewById<TextView>(R.id.tvHotelNameDetails)
        val bookingDates = findViewById<TextView>(R.id.tvBookingDatesDetails)
        val roomType = findViewById<TextView>(R.id.tvRoomTypeDetails)
        val bedDetails = findViewById<TextView>(R.id.tvBedDetails)
        val nameClient = findViewById<TextView>(R.id.nameClient)
        val phoneClient = findViewById<TextView>(R.id.phoneClient)
        val btnConfirmBooking = findViewById<Button>(R.id.btnConfirmBooking)
        val btnCancelBooking = findViewById<Button>(R.id.btnCancelBooking)

        // Извлечение данных из Intent
        val bookingId = intent.getStringExtra("booking_id")
        val hotelNameText = intent.getStringExtra("hotel_name")
        val startDate = intent.getStringExtra("start_date")
        val endDate = intent.getStringExtra("end_date")
        val roomTypeText = intent.getStringExtra("room_type")
        val bedDetailsText = intent.getStringExtra("bed_details")
        val hotelImageRes = intent.getIntExtra("hotel_image", R.drawable.hotel_image)
        val clientName = intent.getStringExtra("client_name")
        val clientPhone = intent.getStringExtra("client_phone")

        // Установка данных в элементы интерфейса
        hotelName.text = hotelNameText
        bookingDates.text = "Даты: $startDate - $endDate"
        roomType.text = "Тип номера: $roomTypeText"
        bedDetails.text = "Детали кроватей: $bedDetailsText"
        hotelImage.setImageResource(hotelImageRes)
        nameClient.text = "ФИО: $clientName"
        phoneClient.text = "Телефон: $clientPhone"

        btnConfirmBooking.setOnClickListener {
            if (bookingId != null) {
                firestore.collection("bookings").document(bookingId)
                    .update("status", "1") // Обновляем статус на "1" (подтверждено)
                    .addOnSuccessListener {
                        navigateToEmployeeActivity()
                    }
                    .addOnFailureListener { e ->
                        e.printStackTrace()
                    }
            }
        }

        btnCancelBooking.setOnClickListener {
            if (bookingId != null) {
                firestore.collection("bookings").document(bookingId)
                    .update("status", "2") // Обновляем статус на "2" (отменено)
                    .addOnSuccessListener {
                        navigateToEmployeeActivity()
                    }
                    .addOnFailureListener { e ->
                        e.printStackTrace()
                    }
            }
        }
    }

    private fun navigateToEmployeeActivity() {
        val intent = Intent(this, EmployeeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish() // Закрываем текущую активность
    }
}
