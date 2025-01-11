package com.example.kr.hotel

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kr.booking.BookingActivity
import com.example.kr.R

//Передача данных для карточки
class HotelAdapter(private val hotels: List<Hotel>) : RecyclerView.Adapter<HotelAdapter.HotelViewHolder>() {

    class HotelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.hotel_image)
        val name: TextView = view.findViewById(R.id.hotel_name)
        val description: TextView = view.findViewById(R.id.hotel_description)
        val features: TextView = view.findViewById(R.id.hotel_features)
        val price: TextView = view.findViewById(R.id.hotel_price)
        val bookButton: Button = view.findViewById(R.id.hotel_book_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.hotel_item_layout, parent, false)
        return HotelViewHolder(view)
    }

    override fun onBindViewHolder(holder: HotelViewHolder, position: Int) {
        val hotel = hotels[position]
        holder.name.text = hotel.name
        holder.description.text = hotel.description
        holder.features.text = hotel.location
        holder.price.text = hotel.price
        holder.image.setImageResource(hotel.imageResource)

        holder.bookButton.setOnClickListener {
            val context: Context = holder.itemView.context

            // Передача данных в BookingActivity
            val intent = Intent(context, BookingActivity::class.java).apply {
                putExtra("hotel_name", hotel.name)
                putExtra("hotel_description", hotel.description)
                putExtra("hotel_price", hotel.price)
                putExtra("hotel_image", hotel.imageResource)
                putExtra("hotel_location", hotel.location)
            }

            context.startActivity(intent)
        }
    }

    override fun getItemCount() = hotels.size
}