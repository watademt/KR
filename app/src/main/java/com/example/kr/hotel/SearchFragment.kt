package com.example.kr.hotel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kr.R
import com.google.firebase.firestore.FirebaseFirestore

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val firestore = FirebaseFirestore.getInstance()
        val hotels = mutableListOf<Hotel>()

        firestore.collection("hotels")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val name = document.getString("name") ?: ""
                    val description = document.getString("description") ?: ""
                    val price = document.getString("price") ?: ""
                    val imageResource = document.getString("imageResource") ?: ""
                    val location = document.getString("location") ?: ""

                    hotels.add(Hotel(name, description, price, imageResource, location))
                }

                // Установите адаптер с загруженными данными
                recyclerView.adapter = HotelAdapter(hotels)
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }
}