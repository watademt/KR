package com.example.kr.hotel

data class Hotel(
    val name: String,
    val description: String,
    val price: String,
    val imageResource: String, // Ссылка на ресурс изображения
    val location: String
)