package com.example.kr.trip

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kr.R

class TripsAdapter(
    private val trips: List<Trip>,
    private val context: Context,
    private val onLeaveReview: (Trip) -> Unit,
    private val onRepeatBooking: (Trip) -> Unit
) : RecyclerView.Adapter<TripsAdapter.TripViewHolder>() {

    class TripViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tripName: TextView = itemView.findViewById(R.id.trip_name)
        val tripDetails: TextView = itemView.findViewById(R.id.trip_details)
        val tripImage: ImageView = itemView.findViewById(R.id.trip_image)
        val leaveReview: TextView = itemView.findViewById(R.id.leave_review)
        val repeatBooking: TextView = itemView.findViewById(R.id.repeat_booking)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trip_item, parent, false)
        return TripViewHolder(view)
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val trip = trips[position]
        holder.tripName.text = trip.name
        holder.tripDetails.text = "${trip.location}\n${trip.dates}\n${trip.price}"

        // Преобразование имени ресурса в идентификатор и установка изображения
        val resourceId = context.resources.getIdentifier(trip.imageResource, "drawable", context.packageName)
        holder.tripImage.setImageResource(resourceId)

        holder.leaveReview.setOnClickListener { onLeaveReview(trip) }
        holder.repeatBooking.setOnClickListener {
            onRepeatBooking(trip)
        }
    }

    override fun getItemCount(): Int = trips.size
}
