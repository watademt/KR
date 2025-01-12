package com.example.kr

import android.accounts.Account
import android.content.ContentValues.TAG
import android.content.Intent
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kr.data.MainScreenDataObject
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.database
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar

class RegisterActivity : AppCompatActivity() {
    private lateinit var btnSelectDate: Button
    private lateinit var dbRef: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val usernameEditText = findViewById<EditText>(R.id.etRegisterUsername)
        val passwordEditText = findViewById<EditText>(R.id.etRegisterPassword)
        val confirmPasswordEditText = findViewById<EditText>(R.id.etConfirmPassword)
        val registerButton = findViewById<Button>(R.id.btnRegister)
        btnSelectDate = findViewById(R.id.btnSelectDate)
        val auth = Firebase.auth
        btnSelectDate.setOnClickListener {
            showDatePicker { date -> btnSelectDate.text = date }
        }
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
                                val name = findViewById<EditText>(R.id.etRegisterName).text.toString()
                                val birthday = btnSelectDate.text.toString()
                                val number = findViewById<EditText>(R.id.etRegisterNumber).text.toString()
                                dbRef = FirebaseFirestore.getInstance();
                                val account = com.example.kr.profile.Account(email, name, birthday,number)
                                dbRef.collection("accounts").document(task.result.user?.uid!!).set(account)
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
    private fun showDatePicker(onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            R.style.CustomDatePicker, // Указываем вашу тему
            { _, selectedYear, selectedMonth, selectedDay ->
                val date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                onDateSelected(date)
            },
            year, month, day
        )
        datePickerDialog.show()
    }
}
