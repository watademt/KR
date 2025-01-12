package com.example.kr.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.kr.LoginActivity
import com.example.kr.R
import com.example.kr.data.MainScreenDataObject
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

//Профиль пользователя
class ProfileFragment : Fragment(R.layout.fragment_profile) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//             val navData = MainScreenDataObject
// Не нужно пока что
        // Получение имени пользователя из SharedPreferences
//        val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
//        val username = sharedPreferences.getString("logged_in_username", "Гость")

//        // Отображение имени пользователя
//        val userNameTextView = view.findViewById<TextView>(R.id.tvUserName)
//        userNameTextView.text = navData.toString()
//
        // Кнопка "Управление аккаунтом"
        view.findViewById<View>(R.id.btnAccountManagement).setOnClickListener {
            // Переход на экран управления аккаунтом
            val intent = Intent(requireContext(), AccountManagementActivity::class.java)
            startActivity(intent)
        }
//        // Кнопка "Ваши отзывы"
//        view.findViewById<View>(R.id.btnUserReviews).setOnClickListener {
//            Toast.makeText(requireContext(), "Ваши отзывы", Toast.LENGTH_SHORT).show()
//        }
//
//        // Кнопка "Связаться с нами"
//        view.findViewById<View>(R.id.btnContactUs).setOnClickListener {
//            Toast.makeText(requireContext(), "Связаться с нами", Toast.LENGTH_SHORT).show()
//        }

        // Кнопка "Выход из аккаунта"
        view.findViewById<View>(R.id.btnLogout).setOnClickListener {
            Firebase.auth.signOut()
//            // Очистка SharedPreferences
//            sharedPreferences.edit().clear().apply()

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
