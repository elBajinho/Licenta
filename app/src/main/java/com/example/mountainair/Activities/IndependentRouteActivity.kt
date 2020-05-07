package com.example.mountainair.Activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mountainair.Interfaces.WeatherService
import com.example.mountainair.Model.Route
import com.example.mountainair.Model.Weather.WeatherResponse
import com.example.mountainair.R
import com.example.mountainair.Server.WeatherServiceBuilder
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_independent_route.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IndependentRouteActivity : AppCompatActivity(){

    lateinit var route : Route

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_independent_route)
        intent?.let{
            route = intent.getParcelableExtra<Route>("object")
            Toast.makeText(this,route.Location,Toast.LENGTH_SHORT).show()

            independent_route_description.text= independent_route_description.text.toString()+ route.Description
            independent_route_level.text= independent_route_level.text.toString() + route.Level
            independent_route_location.text=independent_route_location.text.toString()+route.Location
            independent_route_time.text= independent_route_time.text.toString()+route.Time
            independent_route_activities.text = independent_route_activities.text.toString()+ route.Activities

            var photoRef : StorageReference = Firebase.storage.reference.child("routes").child(route.Image)

            Glide.with(this)
                .load(photoRef)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(independent_route_imageView)

            loadWeather()
        }
    }

    private fun loadWeather() {
        val messageService: WeatherService = WeatherServiceBuilder.buildService(WeatherService::class.java)
        val requestCall = messageService.getWeather("Cluj")

        requestCall.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(this@IndependentRouteActivity,response.body()!!.toString(),Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                print(t.toString())
            }
        })
    }

}