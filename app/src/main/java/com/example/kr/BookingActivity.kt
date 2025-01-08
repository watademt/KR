package com.example.kr

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class BookingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        // Получение элементов из макета
        val btnSelectStartDate = findViewById<Button>(R.id.btnSelectStartDate)
        val btnSelectEndDate = findViewById<Button>(R.id.btnSelectEndDate)
        val spinnerRoomType = findViewById<Spinner>(R.id.spinnerRoomType)
        val etNumberOfBeds = findViewById<EditText>(R.id.etNumberOfBeds)
        val spinnerBedType = findViewById<Spinner>(R.id.spinnerBedType)
        val btnConfirmBooking = findViewById<Button>(R.id.btnConfirmBooking)

        // Установка слушателей для выбора дат
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

        // Обработка нажатия на кнопку "Подтвердить бронирование"
        btnConfirmBooking.setOnClickListener {
            val roomType = spinnerRoomType.selectedItem.toString()
            val numberOfBeds = etNumberOfBeds.text.toString()
            val bedType = spinnerBedType.selectedItem.toString()

            if (btnSelectStartDate.text == "Выберите дату заезда" ||
                btnSelectEndDate.text == "Выберите дату выезда" ||
                numberOfBeds.isEmpty()
            ) {
                Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    this,
                    "Бронирование подтверждено:\n$roomType, $numberOfBeds кровати, $bedType\nДаты: ${btnSelectStartDate.text} - ${btnSelectEndDate.text}",
                    Toast.LENGTH_LONG
                ).show()

                // Переход на MainActivity с флагом для загрузки SearchFragment
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("open_fragment", "search")
                startActivity(intent)

                // Завершение BookingActivity
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
            { _, selectedYear, selectedMonth, selectedDay ->
                val date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                onDateSelected(date)
            },
            year, month, day
        )
        datePickerDialog.show()
    }
}
