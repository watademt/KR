package com.example.kr

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kr.data.MainScreenDataObject
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usernameEditText = findViewById<EditText>(R.id.etUsername)
        val passwordEditText = findViewById<EditText>(R.id.etPassword)
        val loginButton = findViewById<Button>(R.id.btnLogin)
        val registerTextView = findViewById<TextView>(R.id.tvRegister)
        val auth = Firebase.auth
        val currentUser = auth.currentUser
// Проверка на авторизованного юзера
        if (currentUser != null) {
            Toast.makeText(
                baseContext,
                "Authentication success.",
                Toast.LENGTH_SHORT,
            ).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
// Авторизация
        loginButton.setOnClickListener {

            val email = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        MainScreenDataObject(
                            task.result.user?.uid!!,
                            task.result.user?.email!!
                        )
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
//            Старая авторизация
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
