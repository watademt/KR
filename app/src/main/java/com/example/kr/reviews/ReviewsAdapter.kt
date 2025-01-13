package com.example.kr.reviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kr.R

class ReviewsAdapter(private val reviews: List<Review>) :
    RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder>() {

    class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val hotelImage: ImageView = itemView.findViewById(R.id.review_hotel_image)
        val hotelName: TextView = itemView.findViewById(R.id.review_hotel_name)
        val rating: TextView = itemView.findViewById(R.id.review_rating)
        val reviewText: TextView = itemView.findViewById(R.id.review_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.review_item, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]
        holder.hotelName.text = review.hotelName
        holder.rating.text = "★".repeat(review.rate)
        holder.reviewText.text = review.description
        //Glide.with(holder.itemView.context).load(review.imageUrl).into(holder.hotelImage)
    }

    override fun getItemCount(): Int = reviews.size
}
