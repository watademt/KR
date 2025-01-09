package com.example.kr

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usernameEditText = findViewById<EditText>(R.id.etUsername)
        val passwordEditText = findViewById<EditText>(R.id.etPassword)
        val loginButton = findViewById<Button>(R.id.btnLogin)
        val registerTextView = findViewById<TextView>(R.id.tvRegister)

        loginButton.setOnClickListener {
// Так называемая старая авторизация
//            val username = usernameEditText.text.toString()
//            val password = passwordEditText.text.toString()
//
//            val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
//            sharedPreferences.edit().putString("logged_in_username", username).apply()
//            val registeredUsername = sharedPreferences.getString("registered_username", "")
//            val registeredPassword = sharedPreferences.getString("registered_password", "")
//
//            if (username == registeredUsername && password == registeredPassword) {
//                // Сохранение статуса авторизации
//                sharedPreferences.edit().putBoolean("is_logged_in", true).apply()
//
//                // Переход в MainActivity
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//                finish()
//            } else {
//                Toast.makeText(this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show()
//            }
        }

        // Переход на экран регистрации
        registerTextView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
