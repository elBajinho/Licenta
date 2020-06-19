package com.example.mountainair.Activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mountainair.Adapters.MyPostsAdapter
import com.example.mountainair.Adapters.PostsAdapter
import com.example.mountainair.Interfaces.SimpleCallback
import com.example.mountainair.Model.Post
import com.example.mountainair.Model.PostToDisplay
import com.example.mountainair.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_my_posts.*
import kotlinx.android.synthetic.main.fedd_main.*

class MyPostsActivity : AppCompatActivity(){
    lateinit private var auth: FirebaseAuth
    lateinit private var storageReference: StorageReference
    lateinit var database : DatabaseReference
    lateinit var context: Context
    var userId : String = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_posts)

        storageReference = Firebase.storage.reference
        database = Firebase.database.reference
        auth = FirebaseAuth.getInstance()
        context= this
        userId= auth.currentUser!!.uid



        var postsToDisplay : ArrayList<PostToDisplay> = ArrayList()
        var ref = FirebaseDatabase.getInstance().getReference("posts")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (postSnapshot in dataSnapshot.children) {

                    var description :String = postSnapshot.child("description").getValue().toString()
                    var photoRef : StorageReference = storageReference.child("images").child(postSnapshot.child("userId").getValue().toString()).child(postSnapshot.child("photo").getValue().toString())
                    var username: String =""
                    var photo =postSnapshot.child("photo").getValue().toString()

                    if(userId.equals(postSnapshot.child("userId").getValue().toString())) {
                        getUsername(postSnapshot.child("userId").getValue().toString(), object :
                            SimpleCallback<String> {
                            override fun callback(data: String) {
                                username = data

                                var postToDisplay = PostToDisplay(username, description, photoRef, photo, userId)
                                postsToDisplay.add(postToDisplay)

                                myPostsRecyclerView.layoutManager = LinearLayoutManager(context)
                                myPostsRecyclerView.adapter = MyPostsAdapter(context, postsToDisplay)
                            }
                        })
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        ref.addListenerForSingleValueEvent(postListener)


        my_post_button.setOnClickListener{
            goToFedd()
        }

    }

    fun goToFedd(){
        intent = Intent(this, FeddActivity::class.java)
        startActivity(intent)
        Toast.makeText(this, "Your avatar have been uploaded", Toast.LENGTH_SHORT).show()
        finish()
    }

    fun getUsername(userId : String, @NonNull finishedCallback : SimpleCallback<String>){
        var ref = database.child("users").child(userId)
        var username : String = ""
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

    override fun onBackPressed() {
        intent = Intent(this, FeddActivity::class.java)
        startActivity(intent)
        Toast.makeText(this, "Your avatar have been uploaded", Toast.LENGTH_SHORT).show()
        finish()
    }

}