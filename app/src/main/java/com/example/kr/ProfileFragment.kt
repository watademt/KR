package com.example.kr

import android.content.Context
import android.content.Intent
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
            Toast.makeText(requireContext(), "Управление аккаунтом", Toast.LENGTH_SHORT).show()
        }

        // Кнопка "Ваши отзывы"
        view.findViewById<View>(R.id.btnUserReviews).setOnClickListener {
            Toast.makeText(requireContext(), "Ваши отзывы", Toast.LENGTH_SHORT).show()
        }

        // Кнопка "Выход из аккаунта"
        view.findViewById<View>(R.id.btnLogout).setOnClickListener {
            // Очистка SharedPreferences
            sharedPreferences.edit().clear().apply()

            // Очистка кэша
            requireContext().cacheDir.deleteRecursively()

            // Уведомление пользователя
            Toast.makeText(requireContext(), "Вы вышли из аккаунта. Кэш очищен.", Toast.LENGTH_SHORT).show()

            // Переход на экран входа
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Удаляем историю переходов
            startActivity(intent)

            // Завершаем текущую активность
            requireActivity().finish()
        }

    }
}
