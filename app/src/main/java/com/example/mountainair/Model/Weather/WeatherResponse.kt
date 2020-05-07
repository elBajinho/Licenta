package com.example.mountainair.Model.Weather

import com.example.mountainair.Model.Weather.City

data class WeatherResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<DailyWeather>,
    val message: Int
)