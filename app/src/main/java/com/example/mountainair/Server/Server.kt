package com.example.mountainair.Server

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
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
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.android.synthetic.main.activity_update_profile.*
import java.io.ByteArrayOutputStream
import androidx.annotation.NonNull



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