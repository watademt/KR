package com.example.kr

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookingAdapter(
    private val bookings: List<Booking>
) : RecyclerView.Adapter<BookingAdapter.BookingViewHolder>() {

    class BookingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val hotelImage: ImageView = view.findViewById(R.id.hotelImage)
        val hotelName: TextView = view.findViewById(R.id.tvHotelName)
        val userName: TextView = view.findViewById(R.id.tvUserName)
        val bookingDates: TextView = view.findViewById(R.id.tvBookingDates)
        val bookingPrice: TextView = view.findViewById(R.id.tvBookingPrice)
        val detailsButton: Button = view.findViewById(R.id.btnBookingDetails)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.booking_item, parent, false)
        return BookingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val booking = bookings[position]

        holder.hotelName.text = booking.hotelName
        holder.userName.text = booking.userName
        holder.bookingDates.text = "${booking.startDate} - ${booking.endDate}"
        holder.bookingPrice.text = "Цена: ${booking.price}"
        holder.hotelImage.setImageResource(booking.hotelImageRes)

        holder.detailsButton.setOnClickListener {
            val context: Context = holder.itemView.context
            val intent = Intent(context, BookingDetailsActivity::class.java).apply {
                putExtra("booking_id", booking.id)
                putExtra("hotel_name", booking.hotelName)
                putExtra("start_date", booking.startDate)
                putExtra("end_date", booking.endDate)
                putExtra("price", booking.price)
                putExtra("room_type", booking.roomType)
                putExtra("bed_details", booking.bedDetails)
                putExtra("hotel_image", booking.hotelImageRes)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = bookings.size
}
