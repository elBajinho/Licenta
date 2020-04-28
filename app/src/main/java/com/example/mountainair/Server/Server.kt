package com.example.mountainair.Server

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.widget.Toast
import com.example.mountainair.Interfaces.SimpleCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.android.synthetic.main.activity_update_profile.*
import java.io.ByteArrayOutputStream
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mountainair.Adapters.ActivitiesFragmentAdapter
import com.example.mountainair.Model.Route
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.fragment_activities.*


class Server() {
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

    fun getRoutes(finishedCallback: SimpleCallback<ArrayList<Route>>){
        var list : ArrayList<Route> = ArrayList()
        var ref : DatabaseReference = FirebaseDatabase.getInstance().getReference("Arii")
        Log.i("plm2","se fute")

        val carpatsListener = object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.i("plm","se fute")
                for(charpatsSnaphot in dataSnapshot.children){
                    for(mountainsSnapshot in charpatsSnaphot.child("Munti").children){
                        for(toursSnapshot in mountainsSnapshot.child("Trasee").children){
                            var photo = toursSnapshot.child("Imagine").getValue().toString()
                            var location = charpatsSnaphot.child("Nume").getValue().toString()+" "+ mountainsSnapshot.child("Nume").getValue().toString()+" "
                            for ( judete in toursSnapshot.child("Judete").children){
                                location += judete.getValue().toString()+" "
                            }
                            var activities : String = ""
                            for(activity in toursSnapshot.child("Activitati").children){
                                activities += activity.getValue().toString()+" "
                            }
                            var description : String = toursSnapshot.child("Descriere").getValue().toString()
                            var duration : String = toursSnapshot.child("DurataMin").getValue().toString()+" - "+ toursSnapshot.child("DurataMax").getValue().toString()+"h"
                            var level : String = toursSnapshot.child("Greutate").getValue().toString()

                            var route = Route(photo,location,activities,description,duration,level)
                            list.add(route)
                        }
                    }
                }
                finishedCallback.callback(list)
            }
        }
        ref.addListenerForSingleValueEvent(carpatsListener)
    }

    fun getMountains(finishedCallback: SimpleCallback<ArrayList<String>>){
        var list : ArrayList<String> = ArrayList()
        var ref : DatabaseReference = database.child("Arii")

        val mountainsListener = object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                list.add("oricare")
                for(carpatsSnashot in dataSnapshot.children){
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

    fun getPeaks(finishedCallback: SimpleCallback<ArrayList<String>>){
        var list : ArrayList<String> = ArrayList()
        var ref : DatabaseReference = database.child("Varfuri")

        val peaksListener = object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                list.add("oricare")
                for(peaksSnapshot in dataSnapshot.children){
                    list.add(peaksSnapshot.child("Nume").getValue().toString())
                }
                finishedCallback.callback(list)
            }
        }
        ref.addListenerForSingleValueEvent(peaksListener)
    }

    fun getJudete(finishedCallback: SimpleCallback<ArrayList<String>>){
        var list : ArrayList<String> = ArrayList()
        var ref : DatabaseReference = database.child("Judete")

        val judeteListener = object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                list.add("oricare")
                for(judeteSnapshot in dataSnapshot.children){
                    list.add(judeteSnapshot.child("Nume").getValue().toString())
                }
                finishedCallback.callback(list)
            }
        }
        ref.addListenerForSingleValueEvent(judeteListener)
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
}