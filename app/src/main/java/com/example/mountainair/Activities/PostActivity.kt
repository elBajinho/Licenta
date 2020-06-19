package com.example.mountainair.Activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_post.*
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.Toast
import com.example.mountainair.Interfaces.SimpleCallback
import com.example.mountainair.Model.Post
import com.example.mountainair.Server.Service
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream


class PostActivity : AppCompatActivity(){

    var PICK_IMAGE : Int  = 1
    var storage = Firebase.storage
    var storageRef = storage.reference
    var database: DatabaseReference = Firebase.database.reference
    var userId : String = FirebaseAuth.getInstance().getCurrentUser()!!.getUid()
    var service : Service = Service()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.mountainair.R.layout.activity_post)

        service.getUsernameWithUID(userId, object: SimpleCallback<String>{
            override fun callback(data: String) {
                post_username.text = data
            }
        })

        post_imageId.setOnClickListener{
            pickImage()
        }

        post_button.setOnClickListener {
            uploadImage()
            //goToFedd()
        }

    }

    fun uploadImage() {

        post_imageId.isDrawingCacheEnabled = true
        post_imageId.buildDrawingCache()
        val bitmap = (post_imageId.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val imagesRef = storageRef.child("images")
        val userRef = imagesRef.child(userId)
        val now=System.currentTimeMillis().toString()
        val imageRef = userRef.child(now)

        var uploadTask = imageRef.putBytes(data)
        uploadTask.addOnFailureListener {

            goToFedd()
        }.addOnSuccessListener {

            var post= Post(userId, post_description.text.toString(), now)
            database.child("posts").child(now+"User:"+userId).setValue(post)

            goToFedd()
        }
    }

    fun goToFedd(){
        intent = Intent(this, FeddActivity::class.java)
        startActivity(intent)
        Toast.makeText(this, "Your post have been uploaded", Toast.LENGTH_SHORT).show()
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
            post_imageId.setImageURI(data?.data)
            Toast.makeText(this,data.toString(),Toast.LENGTH_SHORT).show()
        }
        else
            Toast.makeText(this,"nope",Toast.LENGTH_SHORT).show()
    }
}