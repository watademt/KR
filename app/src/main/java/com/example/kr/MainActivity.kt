package com.example.kr

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.kr.Hotel.SearchFragment
import com.example.kr.Profile.ProfileFragment
import com.example.kr.Trip.TripsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Проверка работы firestore(Потом удалить говно)
//        val fs = Firebase.firestore
//        fs.collection("hotels")
//            .document().set(mapOf("name" to "Visockiy"))
        setContentView(R.layout.activity_main)

        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)
// Если это включить то вход не работает. На странице LoginActivity добавлена проверка на авторизованного пользоваетля
//        if (!isLoggedIn) {
//            val intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)
//            finish()
//            return
//        }

        val openFragment = intent.getStringExtra("open_fragment")

        // Проверяем, нужно ли открыть конкретный фрагмент
        if (openFragment == "search") {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SearchFragment())
                .commit()
        } else if (savedInstanceState == null) {
            // Установить стартовый фрагмент
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SearchFragment())
                .commit()
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
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
