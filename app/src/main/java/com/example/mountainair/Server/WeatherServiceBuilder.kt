package com.example.mountainair.Server


import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherServiceBuilder {

    private var url : String ="https://api.openweathermap.org/data/2.5/"

    private val okHttp : OkHttpClient.Builder = OkHttpClient.Builder()

    private val builder : Retrofit.Builder= Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())

    private val retrofit : Retrofit = builder.build()

    fun <T> buildService(serviceType:Class<T>) : T{

        return retrofit.create(serviceType)
    }
}