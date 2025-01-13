package com.example.kr.trip

data class Trip(
    val name: String,
    val location: String,
    val dates: String,
    val price: String,
    val pricePerNight: String,
    val description: String,
    val imageResource: String // Имя ресурса изображения
)