package com.example.kr

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class BookingDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_details)

        val hotelImage = findViewById<ImageView>(R.id.hotelImage)
        val hotelName = findViewById<TextView>(R.id.tvHotelNameDetails)
        val bookingDates = findViewById<TextView>(R.id.tvBookingDatesDetails)
        val roomType = findViewById<TextView>(R.id.tvRoomTypeDetails)
        val bedDetails = findViewById<TextView>(R.id.tvBedDetails)
        val btnConfirmBooking = findViewById<Button>(R.id.btnConfirmBooking)
        val btnCancelBooking = findViewById<Button>(R.id.btnCancelBooking)

        // Получение данных из Intent
        val hotelNameText = intent.getStringExtra("hotel_name")
        val startDate = intent.getStringExtra("start_date")
        val endDate = intent.getStringExtra("end_date")
        val price = intent.getStringExtra("price")
        val roomTypeText = intent.getStringExtra("room_type")
        val bedDetailsText = intent.getStringExtra("bed_details")
        val hotelImageRes = intent.getIntExtra("hotel_image", 0)

        // Установка данных
        hotelName.text = hotelNameText
        bookingDates.text = "$startDate - $endDate"
        roomType.text = "Тип комнаты: $roomTypeText"
        bedDetails.text = bedDetailsText
        hotelImage.setImageResource(hotelImageRes)

        // Обработка кнопок
        btnConfirmBooking.setOnClickListener {
            Toast.makeText(this, "Бронирование подтверждено!", Toast.LENGTH_SHORT).show()
            finish()
        }

        btnCancelBooking.setOnClickListener {
            Toast.makeText(this, "Бронирование отменено!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
