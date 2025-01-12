package com.example.kr.booking

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kr.MainActivity
import com.example.kr.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class BookingActivity : AppCompatActivity() {

    private lateinit var hotelImage: ImageView
    private lateinit var hotelName: TextView
    private lateinit var hotelDescription: TextView
    private lateinit var hotelPrice: TextView
    private lateinit var hotelLocation: TextView

    private lateinit var btnSelectStartDate: Button
    private lateinit var btnSelectEndDate: Button
    private lateinit var spinnerRoomType: Spinner
    private lateinit var spinnerBedType: Spinner
    private lateinit var btnConfirmBooking: Button

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        // Инициализация элементов интерфейса
        hotelImage = findViewById(R.id.hotelImage)
        hotelName = findViewById(R.id.hotelName)
        hotelDescription = findViewById(R.id.hotelDescription)
        hotelPrice = findViewById(R.id.hotelPrice)
        hotelLocation = findViewById(R.id.hotelLocation)

        btnSelectStartDate = findViewById(R.id.btnSelectStartDate)
        btnSelectEndDate = findViewById(R.id.btnSelectEndDate)
        spinnerRoomType = findViewById(R.id.spinnerRoomType)
        spinnerBedType = findViewById(R.id.spinnerBedType)
        btnConfirmBooking = findViewById(R.id.btnConfirmBooking)

        // Получение данных об отеле из Intent
        val hotelNameText = intent.getStringExtra("hotel_name")
        val hotelDescriptionText = intent.getStringExtra("hotel_description")
        val hotelPriceText = intent.getStringExtra("hotel_price")
        val hotelImageRes = intent.getIntExtra("hotel_image", 0)
        val hotelLocationText = intent.getStringExtra("hotel_location")

        // Установка данных в элементы интерфейса
        hotelName.text = hotelNameText
        hotelDescription.text = hotelDescriptionText
        hotelPrice.text = hotelPriceText
        hotelImage.setImageResource(hotelImageRes)
        hotelLocation.text = hotelLocationText

        // Настройка выбора дат
        btnSelectStartDate.setOnClickListener {
            showDatePicker { date -> btnSelectStartDate.text = date }
        }
        btnSelectEndDate.setOnClickListener {
            showDatePicker { date -> btnSelectEndDate.text = date }
        }

        // Настройка спиннеров
        spinnerRoomType.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            listOf("Одноместный", "Двухместный", "Люкс")
        )
        spinnerBedType.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            listOf("Односпальная", "Двуспальная", "Кинг-сайз")
        )

        // Обработка кнопки подтверждения бронирования
        btnConfirmBooking.setOnClickListener {
            val roomType = spinnerRoomType.selectedItem.toString()
            val numberOfBeds = findViewById<EditText>(R.id.etNumberOfBeds).text.toString()
            val bedType = spinnerBedType.selectedItem.toString()
            val startDate = btnSelectStartDate.text.toString()
            val endDate = btnSelectEndDate.text.toString()

            if (startDate == "Выберите дату заезда" ||
                endDate == "Выберите дату выезда" ||
                numberOfBeds.isEmpty()
            ) {
                Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_SHORT).show()
            } else {
                createBooking(
                    hotelNameText ?: "",
                    hotelDescriptionText ?: "",
                    hotelPriceText ?: "",
                    hotelLocationText ?: "",
                    startDate,
                    endDate,
                    roomType,
                    bedType,
                    numberOfBeds
                )
            }
        }
    }

    private fun createBooking(
        hotelName: String,
        hotelDescription: String,
        hotelPrice: String, // Цена за ночь
        hotelLocation: String,
        startDate: String,
        endDate: String,
        roomType: String,
        bedType: String,
        numberOfBeds: String
    ) {
        // Получаем UID текущего пользователя
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Toast.makeText(this, "Вы не авторизованы!", Toast.LENGTH_SHORT).show()
            return
        }

        val userId = currentUser.uid

        // Получаем данные клиента из коллекции `clients`
        firestore.collection("clients").document(userId).get()
            .addOnSuccessListener { clientDocument ->
                if (clientDocument.exists()) {
                    val clientName = clientDocument.getString("ФИО") ?: "Неизвестно"
                    val clientPhone = clientDocument.getString("Телефон") ?: "Неизвестно"
                    val clientDOB = clientDocument.getString("Дата рождение") ?: "Неизвестно"

                    // Вычисляем количество ночей
                    val nights = calculateDaysBetween(startDate, endDate)
                    if (nights <= 0) {
                        Toast.makeText(this, "Выберите корректные даты!", Toast.LENGTH_SHORT).show()
                        return@addOnSuccessListener
                    }

                    // Преобразуем цену за ночь в Double
                    val pricePerNight = hotelPrice.toDoubleOrNull() ?: 0.0
                    val totalPrice = pricePerNight * nights // Итоговая цена

                    // Данные бронирования
                    val bookingData = mapOf(
                        "hotelName" to hotelName,
                        "hotelDescription" to hotelDescription,
                        "hotelPrice" to totalPrice, // Итоговая цена
                        "hotelLocation" to hotelLocation,
                        "startDate" to startDate,
                        "endDate" to endDate,
                        "roomType" to roomType,
                        "bedType" to bedType,
                        "numberOfBeds" to numberOfBeds,
                        "nights" to nights, // Количество ночей
                        "clientName" to clientName,
                        "clientPhone" to clientPhone,
                        "clientDOB" to clientDOB,
                        "clientUID" to userId
                    )

                    // Сохраняем данные в коллекции `bookings`
                    firestore.collection("bookings")
                        .add(bookingData)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Бронирование успешно оформлено!", Toast.LENGTH_SHORT).show()
                            // Переход на главную страницу
                            val intent = Intent(this, MainActivity::class.java)
                            intent.putExtra("open_fragment", "search")
                            startActivity(intent)
                            finish()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Ошибка оформления бронирования!", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(this, "Информация о клиенте не найдена!", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Ошибка получения данных клиента!", Toast.LENGTH_SHORT).show()
            }
    }

    private fun calculateDaysBetween(startDate: String, endDate: String): Int {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return try {
            val start = dateFormat.parse(startDate)
            val end = dateFormat.parse(endDate)
            val difference = end.time - start.time
            (difference / (1000 * 60 * 60 * 24)).toInt()
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

    private fun showDatePicker(onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                onDateSelected(date)
            },
            year, month, day
        )
        datePickerDialog.show()
    }
}