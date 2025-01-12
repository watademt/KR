package com.example.kr

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kr.data.MainScreenDataObject
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val usernameEditText = findViewById<EditText>(R.id.etRegisterUsername)
        val passwordEditText = findViewById<EditText>(R.id.etRegisterPassword)
        val confirmPasswordEditText = findViewById<EditText>(R.id.etConfirmPassword)
        val registerButton = findViewById<Button>(R.id.btnRegister)
        val auth = Firebase.auth
// Регистрация
        registerButton.setOnClickListener {

            val email = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (email.isNotEmpty() || password.isNotEmpty() || confirmPassword.isNotEmpty()) {
                if (password == confirmPassword) {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success")
                                Toast.makeText(
                                    baseContext,
                                    "Authentication Successful.",
                                    Toast.LENGTH_SHORT,
                                ).show()
                                MainScreenDataObject(
                                    task.result.user?.uid!!,
                                    task.result.user?.email!!
                                )
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.exception)
                                Toast.makeText(
                                    baseContext,
                                    "Authentication failed.",
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }
                        }

                } else {
                    Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            } else {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }
    }
}
