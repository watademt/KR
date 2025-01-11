package com.example.kr.Booking

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.kr.MainActivity
import com.example.kr.R
import java.util.Calendar

//Обработка кнопок
class BookingActivity : AppCompatActivity() {

    private lateinit var hotelImage: ImageView
    private lateinit var hotelName: TextView
    private lateinit var hotelDescription: TextView
    private lateinit var hotelPrice: TextView
    private lateinit var hotelLocation: TextView

    // Добавляем переменные для кнопок и спиннеров
    private lateinit var btnSelectStartDate: Button
    private lateinit var btnSelectEndDate: Button
    private lateinit var spinnerRoomType: Spinner
    private lateinit var spinnerBedType: Spinner
    private lateinit var btnConfirmBooking: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        // Инициализация элементов макета
        hotelImage = findViewById(R.id.hotelImage)
        hotelName = findViewById(R.id.hotelName)
        hotelDescription = findViewById(R.id.hotelDescription)
        hotelPrice = findViewById(R.id.hotelPrice)
        hotelLocation = findViewById(R.id.hotelLocation)

        // Инициализация кнопок и спиннеров
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

        // Установка данных в элементы макета
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

        // Подтверждение бронирования
        btnConfirmBooking.setOnClickListener {
            val roomType = spinnerRoomType.selectedItem.toString()
            val numberOfBeds = findViewById<EditText>(R.id.etNumberOfBeds).text.toString()
            val bedType = spinnerBedType.selectedItem.toString()

            if (btnSelectStartDate.text == "Выберите дату заезда" ||
                btnSelectEndDate.text == "Выберите дату выезда" ||
                numberOfBeds.isEmpty()
            ) {
                Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Бронирование подтверждено!", Toast.LENGTH_SHORT).show()

                // Переход на MainActivity с флагом для загрузки FragmentSearch
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("open_fragment", "search")
                startActivity(intent)

                // Завершаем BookingActivity
                finish()
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