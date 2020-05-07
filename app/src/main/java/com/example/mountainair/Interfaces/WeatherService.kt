package com.example.mountainair.Interfaces

import com.example.mountainair.Model.Weather.WeatherResponse
import retrofit2.Call
import retrofit2.http.*

interface WeatherService{

    @GET("forecast?appid=cb7b3423e25e1dec15140e340a0efcdd")
    fun getWeather(@Query("q") city : String) : Call<WeatherResponse>


}