package com.example.kr.profile

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kr.MainActivity
import com.example.kr.R
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar

//Изменения аккаунта
class AccountManagementActivity : AppCompatActivity() {

    private lateinit var ivProfileAvatar: ImageView
    private lateinit var etUserName: EditText
    private lateinit var etDateOfBirth: EditText
    private lateinit var etUserEmail: EditText
    private lateinit var etPhoneNumber: EditText
 //   private lateinit var etPassword: EditText
    private lateinit var btnSaveChanges: Button
    private lateinit var btnChangeAvatar: Button

    private val PICK_IMAGE_REQUEST = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_management)

        // Инициализация элементов
        ivProfileAvatar = findViewById(R.id.ivProfileAvatar)
        etUserName = findViewById(R.id.etUserName)
        etUserEmail = findViewById(R.id.etUserEmail)
        etDateOfBirth = findViewById(R.id.etDateOfBirth)
        etPhoneNumber = findViewById(R.id.etPhoneNumber)
//        etPassword = findViewById(R.id.etPassword)
        btnSaveChanges = findViewById(R.id.btnSaveChanges)
        btnChangeAvatar = findViewById(R.id.btnChangeAvatar)

        // Получение текущих данных (заменить на данные из базы данных)
        val user = Firebase.auth.currentUser
        val dbRef = FirebaseFirestore.getInstance();

        dbRef.collection("accounts").document(user!!.uid).get()
            .addOnSuccessListener { document->
                if(document !=null && document.exists()){
                    etUserEmail.setText(document.getString("email") ?: "")
                    etUserName.setText(document.getString("name") ?: "")
                    etDateOfBirth.setText(document.getString("birthday") ?: "")
                    etPhoneNumber.setText(document.getString("number") ?: "")
                }else{
                    Toast.makeText(
                        baseContext,
                        "else",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }



        // Установка текущих данных в поля
 //       etPassword.setText(currentPassword)

        // Слушатель выбора даты
        etDateOfBirth.setOnClickListener {
            showDatePicker { date -> etDateOfBirth.setText(date) }
        }

        // Слушатель выбора фото профиля
        btnChangeAvatar.setOnClickListener {
            openGallery()
        }
        // Сохранение изменений
        btnSaveChanges.setOnClickListener {
            val newUserName = etUserName.text.toString()
            val newDateOfBirth = etDateOfBirth.text.toString()
            val newPhoneNumber = etPhoneNumber.text.toString()
            val newUserEmail = etUserEmail.text.toString()
//            val newPassword = etPassword.text.toString()

            if (newUserName.isNotEmpty() && newDateOfBirth.isNotEmpty()
                && newPhoneNumber.isNotEmpty()
            ) {
                val dbRef = FirebaseFirestore.getInstance();
                val account = com.example.kr.profile.Account(newUserEmail, newUserName, newDateOfBirth,newPhoneNumber)
                val user = Firebase.auth.currentUser
                dbRef.collection("accounts").document(user?.uid!!).set(account)
                // Сохранение изменений (заменить на обновление в базе данных)
                Toast.makeText(this, "Изменения сохранены", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // Метод для открытия галереи
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    // Обработка результата выбора фотографии
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data
            if (selectedImageUri != null) {
                ivProfileAvatar.setImageURI(selectedImageUri)
            } else {
                Toast.makeText(this, "Ошибка выбора изображения", Toast.LENGTH_SHORT).show()
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
            R.style.CustomDatePicker,
            { _, selectedYear, selectedMonth, selectedDay ->
                val date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                onDateSelected(date)
            },
            year, month, day
        )
        datePickerDialog.show()
    }
}
