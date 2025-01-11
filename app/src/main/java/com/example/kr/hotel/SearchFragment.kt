package com.example.kr.hotel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kr.R

//Фрагмент поиска в панели навигации
class SearchFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Список отелей
        val hotels = listOf(
            Hotel(
                "Rios Beach",
                "Небольшой отель для экономичного отдыха расположен в Бельдиби, в шаговой доступности от собственного пляжа. На территории — один отельный корпус, открытый бассейн, теннисный корт. Прогулка до центра поселка займет 15-20 минут.",
                "167 225 руб.",
                R.drawable.hotel_image,
                "Бельдиби, Турция"
            ),
            Hotel(
                "Club com.example.kr.Hotel.Hotel Anjeliq",
                "Отель с бассейнами и горками",
                "198 285 руб.",
                R.drawable.hotel_image,
                "Алания, Турция"
            ),
            Hotel(
                "Грейс Аква Вилла",
                "Отель с первой линией на берегу",
                "160 417 руб.",
                R.drawable.hotel_image,
                "Сочи, Россия"
            )
        )

        // Настройка RecyclerView
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = HotelAdapter(hotels)
    }
}