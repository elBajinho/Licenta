package com.example.mountainair.Server

import com.example.mountainair.Interfaces.SimpleCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

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

    fun getAvatarReference(uid: String) : StorageReference{
        return  storageReference.child("images").child(uid).child("avatar")
    }

}