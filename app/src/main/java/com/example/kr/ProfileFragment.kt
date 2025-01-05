package com.example.kr

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Получение имени пользователя из SharedPreferences
        val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("logged_in_username", "Гость")

        // Отображение имени пользователя
        val userNameTextView = view.findViewById<TextView>(R.id.tvUserName)
        userNameTextView.text = username
        // Кнопка "Управление аккаунтом"
        view.findViewById<View>(R.id.btnAccountManagement).setOnClickListener {
            // Логика управления аккаунтом
            Toast.makeText(requireContext(), "Управление аккаунтом", Toast.LENGTH_SHORT).show()
        }

        // Кнопка "Ваши отзывы"
        view.findViewById<View>(R.id.btnUserReviews).setOnClickListener {
            // Логика для просмотра отзывов
            Toast.makeText(requireContext(), "Ваши отзывы", Toast.LENGTH_SHORT).show()
        }

        // Кнопка "Связаться с нами"
        view.findViewById<View>(R.id.btnContactUs).setOnClickListener {
            // Логика для контактов
            Toast.makeText(requireContext(), "Связаться с нами", Toast.LENGTH_SHORT).show()
        }

        // Кнопка "Выход из аккаунта"
        view.findViewById<View>(R.id.btnLogout).setOnClickListener {
            // Логика выхода из аккаунта
            Toast.makeText(requireContext(), "Выход из аккаунта", Toast.LENGTH_SHORT).show()
        }
    }
}
