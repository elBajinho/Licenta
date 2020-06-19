package com.example.mountainair.Server

import android.content.ContentValues.TAG
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.mountainair.Interfaces.SimpleCallback
import com.example.mountainair.Interfaces.WeatherService
import com.example.mountainair.Model.*
import com.example.mountainair.Model.Weather.WeatherResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import java.util.Date


class Service() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var storageReference: StorageReference

    init{
        storageReference = Firebase.storage.reference
        database = Firebase.database.reference
        auth = FirebaseAuth.getInstance()
    }

    fun getUsernameWithUID(uid: String, finishedCallback : SimpleCallback<String> ){
            var ref = database.child("users").child(uid)

            val userListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    finishedCallback.callback(dataSnapshot.child("username").getValue().toString())
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    finishedCallback.callback("Jane Tho")
                }
            }
            ref.addValueEventListener(userListener)
    }

    fun getActivities(finishedCallback : SimpleCallback<ArrayList<String>>){
        var list : ArrayList<String> = ArrayList()
        var ref :DatabaseReference = FirebaseDatabase.getInstance().getReference("activities")
        val activitiesListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (activitySnapshot in dataSnapshot.children) {
                    var activity = activitySnapshot.child("name").getValue().toString()
                    list.add(activity)
                }
                finishedCallback.callback(list)
            }
        }
        ref.addListenerForSingleValueEvent(activitiesListener)
    }

    fun getCharpats(finishedCallback: SimpleCallback<ArrayList<String>>){
        var list : ArrayList<String> = ArrayList()
        var ref : DatabaseReference = FirebaseDatabase.getInstance().getReference("Arii")

        val carpatsListener = object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                list.add("oricare")
                for(carpatsSnashot in dataSnapshot.children){
                 list.add(carpatsSnashot.child("Nume").getValue().toString())
                }
                finishedCallback.callback(list)
            }
        }
        ref.addListenerForSingleValueEvent(carpatsListener)
    }

    fun getRoutes(filter : Filters, finishedCallback: SimpleCallback<ArrayList<Route>>){
        var list : ArrayList<Route> = ArrayList()
        var ref : DatabaseReference = FirebaseDatabase.getInstance().getReference("Arii")

        Log.d("geography",filter.gs.toString())

        val carpatsListener = object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(charpatsSnaphot in dataSnapshot.children){
                    for(mountainsSnapshot in charpatsSnaphot.child("Munti").children){
                        for(toursSnapshot in mountainsSnapshot.child("Trasee").children){
                            if(validGeography(filter.gs,
                                    charpatsSnaphot.child("Nume").getValue().toString(),
                                    mountainsSnapshot.child("Nume").getValue().toString(),
                                    toursSnapshot.child("Varfuri").children,
                                    toursSnapshot.child("Judete").children)) {
                                if(validActivities(filter.activities,  toursSnapshot.child("Activitati").children )) {
                                    if (validRoute(
                                                filter.rs,
                                                toursSnapshot.child("DurataMin").getValue().toString().toInt(),
                                                toursSnapshot.child("DurataMax").getValue().toString().toInt(),
                                                toursSnapshot.child("Greutate").getValue().toString()
                                            )
                                        ) {
                                            var weatherResponse : WeatherResponse

                                            getWheather(toursSnapshot.child("Localitate").getValue().toString(),
                                                object : SimpleCallback<WeatherResponse> {
                                                override fun callback(data: WeatherResponse) {
                                                    weatherResponse = data

                                                    if(validWeather(filter.date,filter.ws, weatherResponse)) {
                                                        var city = toursSnapshot.child("Localitate").getValue().toString()
                                                        var photo = toursSnapshot.child("Imagine")
                                                                .getValue()
                                                                .toString()

                                                        var location =
                                                            charpatsSnaphot.child("Nume").getValue().toString() + " " + mountainsSnapshot.child(
                                                                "Nume"
                                                            ).getValue().toString() + " "

                                                        for (judete in toursSnapshot.child("Judete").children) {
                                                            location += judete.getValue().toString() + " "
                                                        }

                                                        var activities: String = ""
                                                        for (activity in toursSnapshot.child("Activitati").children) {
                                                            activities += activity.getValue().toString() + " "
                                                        }

                                                        var description: String =
                                                            toursSnapshot.child("Descriere")
                                                                .getValue()
                                                                .toString()
                                                        var duration: String =
                                                            toursSnapshot.child("DurataMin").getValue().toString() + " - " + toursSnapshot.child(
                                                                "DurataMax"
                                                            ).getValue().toString() + "h"
                                                        var level: String =
                                                            toursSnapshot.child("Greutate")
                                                                .getValue()
                                                                .toString()

                                                        var route =
                                                            Route(
                                                                photo,
                                                                location,
                                                                activities,
                                                                description,
                                                                duration,
                                                                level,
                                                                city
                                                            )
                                                        list.add(route)
                                                    finishedCallback.callback(list)
                                                    }
                                                }
                                            })
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        ref.addListenerForSingleValueEvent(carpatsListener)
        }

    private fun validWeather(date: Date, ws: WheatherSelection?, weatherResponse: WeatherResponse): Boolean {

        var currentDate : Date = Date()
        var nextDate : Date = date
        var nod = (nextDate.time -currentDate.time)/(1000 * 60 * 60 * 24)
        var ok =true


        Log.i("zile :", nod.toString())
        var processedWeather : WheatherSelection = getWeatherProcessed(nod, weatherResponse)


        Log.i("vremea procesata", processedWeather.toString())
        if(ws!!.rain==true && processedWeather.rain==true){
            ok = false
        }

        if(processedWeather.maxT>ws.maxT){
            ok = false
        }

        if(processedWeather.minT<ws.minT){
            ok=false
        }

        if(processedWeather.maxW>ws.maxW){
            ok=false
        }

        if(processedWeather.minW<ws.minW){
            ok=false
        }

        return ok
    }

    fun getWeatherProcessed(nod: Long, wr: WeatherResponse): WheatherSelection {
        var weatherProcessed :WheatherSelection=WheatherSelection(30,0,30,0,false,false)

        weatherProcessed.rain=false

        var k = nod

        if(k>4){
            k= 4
        }

        for (i in 8*k..8*k+7) {
            var j= i.toInt()

            if(wr.list[j].main.temp_min-273.15<weatherProcessed.minT)
                weatherProcessed.minT= (wr.list[j].main.temp_min-273.15).toInt()

            if(wr.list[j].main.temp_max-273.15>weatherProcessed.maxT)
                weatherProcessed.maxT= (wr.list[j].main.temp_max-273.15).toInt()

            if(wr.list[j].wind.speed*3.6<weatherProcessed.minW)
                weatherProcessed.minW=(wr.list[j].wind.speed * 3.6).toInt()

            if(wr.list[j].wind.speed*3.6>weatherProcessed.maxW)
                weatherProcessed.maxW=(wr.list[j].wind.speed*3.6).toInt()

            if(wr.list[j].weather[0].main.equals("Rain")){
                weatherProcessed.rain=true
            }

            weatherProcessed.minW=weatherProcessed.minW
            weatherProcessed.maxW=weatherProcessed.maxW

            weatherProcessed.foog=false
        }
        return weatherProcessed
    }

    private fun validGeography(gs: GeographicSelection?, charpats: String, mountains: String, peaks: Iterable<DataSnapshot>, judete: Iterable<DataSnapshot>): Boolean {
        var ok1 = true
        var ok2= gs!!.Peak.equals("oricare")
        var ok3= gs!!.Judet.equals("oricare")

        if(!charpats.equals(gs!!.Carphats) && !gs.Carphats.equals("oricare")){
            ok1 = false
        }

        if(!mountains.equals(gs.Mountains) && !gs.Mountains.equals("oricare")){
            ok1 = false
        }

        for( i in  peaks){
            if(i.getValue().toString().equals(gs.Peak) )
                ok2 = true
        }

        for( i in judete){
            if(i.getValue().toString().equals(gs.Judet))
                ok3=true
        }

        return ok1 && ok2 && ok3
    }

    private fun validRoute(rs: RouteSelection?, durataMin : Int, duratMax: Int, greutate: String): Boolean {
        var ok = true

        if(!rs!!.difficulties!!.contains(greutate) && !rs.difficulties!!.isEmpty()){
          ok = false
        }

        if(!(rs!!.minH<=durataMin && rs!!.maxH>=duratMax)){
            ok = false
        }
        return ok
    }

    private fun validActivities(activitySelection: ArrayList<String>, children: MutableIterable<DataSnapshot>) : Boolean{

        var ok= false

        for( a in children){
            if(activitySelection.contains(a.getValue().toString()))
                ok=true
        }

        if(activitySelection.isEmpty()){
            ok = true
        }

        return ok
    }

    fun getMountains(finishedCallback: SimpleCallback<ArrayList<String>>, charpats : String= ""){
        var list : ArrayList<String> = ArrayList()
        var ref : DatabaseReference = database.child("Arii")

        val mountainsListener = object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                list.add("oricare")
                for(carpatsSnashot in dataSnapshot.children){
                    if(carpatsSnashot.child("Nume").getValue().toString().equals(charpats) || charpats.equals(""))
                    for(mountainsSnapshot in  carpatsSnashot.children){
                        for(finalSnapshot in mountainsSnapshot.children) {
                            list.add(finalSnapshot.child("Nume").getValue().toString())
                        }
                    }
                }
                finishedCallback.callback(list)
            }
        }
        ref.addListenerForSingleValueEvent(mountainsListener)
    }

    fun getPeaks(finishedCallback: SimpleCallback<ArrayList<String>>,mountains : String=""){
        var list : ArrayList<String> = ArrayList()
        var ref : DatabaseReference = database.child("Varfuri")

        val peaksListener = object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                list.add("oricare")

                for(peaksSnapshot in dataSnapshot.children){
                        if(peaksSnapshot.child("Munte").getValue().toString().equals(mountains) || mountains.equals(""))
                             list.add(peaksSnapshot.child("Nume").getValue().toString())
                }
                finishedCallback.callback(list)
            }
        }
        ref.addListenerForSingleValueEvent(peaksListener)
    }

    fun getJudete(finishedCallback: SimpleCallback<ArrayList<String>>, peak : String = ""){
        var list : ArrayList<String> = ArrayList()
        var ref2 : DatabaseReference = database.child("Arii")

        val judeteListener = object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                list.add("oricare")
                    for(carpatsSnashot in dataSnapshot.children){
                        for(mountainsSnapshot in  carpatsSnashot.children){
                            for(finalSnapshot in mountainsSnapshot.children) {
                                for(traseeSnapshot in finalSnapshot.child("Trasee").children){
                                    for(varfuriSnapshot in traseeSnapshot.child("Varfuri").children){
                                        if(varfuriSnapshot.getValue().toString().equals(peak) || peak.equals("")){
                                            for(judeteSnapshot in traseeSnapshot.child("Judete").children){
                                                if(!list.contains(judeteSnapshot.getValue().toString()))
                                                    list.add(judeteSnapshot.getValue().toString())
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                finishedCallback.callback(list)
            }
        }
            ref2.addListenerForSingleValueEvent(judeteListener)
    }

    fun getAvatarReference(uid: String) : StorageReference?{
        return storageReference.child("images").child(uid).child("avatar")
    }

    fun deletePost(postId : String, userId : String){
        var databaseRef = database.child("posts").child(postId+"User:"+userId)

        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    snapshot.ref.removeValue()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException())
            }
        })

        var storageRef = storageReference.child("images").child(userId).child(postId)
        storageRef.delete().addOnSuccessListener(OnSuccessListener<Void> {
            Log.d(TAG, "onSuccess: deleted file")
        }).addOnFailureListener(OnFailureListener {
            Log.d(TAG, "onFailure: did not delete file")
        })
    }

    fun updatePost(postId: String, userId: String, description : String){
        var databaseRef = database.child("posts").child(postId+"User:"+userId).child("description").setValue(description)
    }

    fun getWheather(city : String, finishedCallback: SimpleCallback<WeatherResponse>) {
        val messageService: WeatherService = WeatherServiceBuilder.buildService(
            WeatherService::class.java)
        val requestCall = messageService.getWeather(city)

        requestCall.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if (response.isSuccessful) {
                    finishedCallback.callback(response.body())
                    Log.i("vreme",response.body().toString())
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                print(t.toString())
            }
        })
    }
}