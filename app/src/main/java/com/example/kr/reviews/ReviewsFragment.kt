package com.example.kr.reviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kr.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ReviewsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reviews, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewReviews)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val firestore = FirebaseFirestore.getInstance()
        val reviews = mutableListOf<Review>()

        // Загрузка отзывов из Firestore
        firestore.collection("reviews")
            .whereEqualTo("userId", FirebaseAuth.getInstance().currentUser?.uid)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val hotelName = document.getString("hotelName") ?: ""
                    val rate = document.getLong("rate")?.toInt() ?: 0
                    val description = document.getString("description") ?: ""
                    //val imageUrl = document.getString("imageUrl") ?: ""

                    reviews.add(Review(hotelName, rate, description))
                }
                recyclerView.adapter = ReviewsAdapter(reviews)
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }
    //Ручное добавление
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)

//        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewReviews)
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Тестовые данные
//        val reviews = listOf(
//            Review("Отель Грейс", 5, "Отличный сервис и удобные номера!"),
//            Review("Рэдиссон Блу", 4, "Хорошо, но есть куда расти."),
//            Review("Ибис", 3, "Средний отель, подходит для кратковременного проживания.")
//        )

//        recyclerView.adapter = ReviewsAdapter(reviews)
//    }

}
