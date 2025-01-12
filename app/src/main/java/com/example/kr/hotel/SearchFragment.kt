package com.example.kr.hotel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kr.R
import com.google.firebase.firestore.FirebaseFirestore

class SearchFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var etCity: EditText
    private lateinit var etDate : EditText
    private lateinit var etGuests : EditText
    private lateinit var btnSearch: Button


    private val firestore = FirebaseFirestore.getInstance()
    private val hotels = mutableListOf<Hotel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        etCity = view.findViewById(R.id.etCity)
        btnSearch = view.findViewById(R.id.btnSearch)

        loadAllHotels()

        btnSearch.setOnClickListener {
            val city = etCity.text.toString().trim()
            searchHotels(city)
        }
    }

    private fun loadAllHotels() {
        firestore.collection("hotels")
            .get()
            .addOnSuccessListener { result ->
                hotels.clear()
                for (document in result) {
                    val name = document.getString("name") ?: ""
                    val description = document.getString("description") ?: ""
                    val price = document.getString("price") ?: ""
                    val imageResource = document.getString("imageResource") ?: ""
                    val location = document.getString("location") ?: ""

                    hotels.add(Hotel(name, description, price, imageResource, location))
                }

                recyclerView.adapter = HotelAdapter(hotels)
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }
// Поиск по городу
    private fun searchHotels(city: String) {
        if (city.isEmpty()) {
            loadAllHotels()
            return
        }

        val filteredHotels = hotels.filter { it.location.contains(city, ignoreCase = true) }
        recyclerView.adapter = HotelAdapter(filteredHotels)
    }
}
