package com.example.kr

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Проверка работы firestore(Потом удалить говно)
//        val fs = Firebase.firestore
//        fs.collection("hotels")
//            .document().set(mapOf("name" to "Visockiy"))

        // Проверка статуса авторизации
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)

        if (!isLoggedIn) {
            // Если пользователь не авторизован, перенаправить на LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Закрыть MainActivity
            return
        }

        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        // Установить стартовый фрагмент
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SearchFragment())
                .commit()
        }

        // Обработчик переключения вкладок
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_search -> {
                    replaceFragment(SearchFragment())
                    true
                }
                R.id.nav_deals -> {
                    replaceFragment(TripsFragment())
                    true
                }
                R.id.nav_profile -> {
                    replaceFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
