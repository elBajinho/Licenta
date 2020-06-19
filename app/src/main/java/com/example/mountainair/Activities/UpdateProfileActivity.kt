package com.example.mountainair.Activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mountainair.R
import com.example.mountainair.Server.Service
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_update_profile.*
import java.io.ByteArrayOutputStream

class UpdateProfileActivity : AppCompatActivity(){

    var PICK_IMAGE : Int  = 1
    var storage = Firebase.storage
    var storageRef = storage.reference
    var database: DatabaseReference = Firebase.database.reference
    var userId : String = FirebaseAuth.getInstance().getCurrentUser()!!.getUid()
    var service : Service = Service()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)

        Glide.with(this)
           .load(service.getAvatarReference(userId))
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(update_profile_image)

        update_profile_button.setOnClickListener {
            var intent = Intent(this, FeddActivity::class.java)
            startActivity(intent)
        }

        update_profile_image.setOnClickListener {
            pickImage()
        }

        update_profile_button.setOnClickListener {
            uploadImage()
        }

    }

    fun pickImage(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE){
            update_profile_image.setImageURI(data?.data)
        }

    }

    fun uploadImage(){
        update_profile_image.isDrawingCacheEnabled = true
        update_profile_image.buildDrawingCache()
        val bitmap = (update_profile_image.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val imagesRef = storageRef.child("images")
        val userRef = imagesRef.child(userId)
        val avatarRef = userRef.child("avatar")

        var uploadTask = avatarRef.putBytes(data)
        uploadTask.addOnFailureListener {
        }.addOnSuccessListener {

            database.child("users").child(userId).child("username").setValue(update_profile_username.text.toString())
            goToFedd()
        }
    }

    fun goToFedd(){
        intent = Intent(this, FeddActivity::class.java)
        startActivity(intent)
        Toast.makeText(this, "Your avatar have been uploaded", Toast.LENGTH_SHORT).show()
        finish()
    }
}