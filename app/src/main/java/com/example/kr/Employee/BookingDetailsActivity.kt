package com.example.kr.Employee

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.kr.R

//Передача данных для подробной инфы об турах
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

        val bookingId = intent.getStringExtra("booking_id")

        btnConfirmBooking.setOnClickListener {
            setResult(RESULT_OK, Intent().apply {
                putExtra("booking_action", "confirmed")
                putExtra("booking_id", bookingId)
            })
            finish()
        }

        btnCancelBooking.setOnClickListener {
            setResult(RESULT_OK, Intent().apply {
                putExtra("booking_action", "cancelled")
                putExtra("booking_id", bookingId)
            })
            finish()
        }
    }
}

