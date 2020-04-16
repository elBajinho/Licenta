package com.example.mountainair.Activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mountainair.Adapters.PostsAdapter
import com.example.mountainair.Interfaces.SimpleCallback
import com.example.mountainair.Model.Post
import com.example.mountainair.Model.PostToDisplay
import com.example.mountainair.Model.User
import com.example.mountainair.R
import com.example.mountainair.Server.Server
import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.android.synthetic.main.fedd_main.*
import kotlinx.android.synthetic.main.fedd_nav_header.*

class FeddActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    lateinit var auth : FirebaseAuth
    lateinit var database : DatabaseReference
    lateinit var storageReference: StorageReference
    var server : Server =Server()
    var userId  : String = FirebaseAuth.getInstance().getCurrentUser()!!.getUid()
    private lateinit var context: Context
    var postsToDisplay: ArrayList<PostToDisplay> = ArrayList()
    var ref : DatabaseReference =FirebaseDatabase.getInstance().getReference("posts")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        auth = FirebaseAuth.getInstance()
        storageReference = Firebase.storage.reference
        database = Firebase.database.reference
        context = this

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.children) {

                    var description :String = postSnapshot.child("description").getValue().toString()
                    var photo : StorageReference = storageReference.child("images").child(postSnapshot.child("userId").getValue().toString()).child(postSnapshot.child("photo").getValue().toString())
                    var username: String =""

                    getUsername(postSnapshot.child("userId").getValue().toString(),object : SimpleCallback<String>{
                        override fun callback(data: String) {
                            username = data

                            var postToDisplay = PostToDisplay(username,description,photo)
                            postsToDisplay.add(postToDisplay)
                            recyclerFeed.layoutManager = LinearLayoutManager(context)
                            recyclerFeed.adapter = PostsAdapter(context, postsToDisplay)
                        }
                    })
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        ref.addListenerForSingleValueEvent(postListener)
        //var posts : ArrayList<Post> = ArrayList()
//        for(i in 0..100){
//            posts.add(Post("Vlad","muntii  astia th0", "https://picsum.photos/600/300?random&"+i))
//        }

        setDrawer()

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_post -> {
                goToPost()
            }
            R.id.nav_my_posts -> {
                goToMyPosts()
            }
            R.id.nav_create_your_journey -> {
                Toast.makeText(this, "Journey clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_update -> {
                goToUpdate()
            }
            R.id.nav_logout -> {
                signOut()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


    fun getUsername(userId : String, @NonNull finishedCallback : SimpleCallback<String>){
        var ref = database.child("users").child(userId)
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

    fun setDrawer(){
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)


        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
        var headerview : android.view.View? = nav_view.getHeaderView(0)
        var headerImgView : ImageView =  headerview!!.findViewById(R.id.drawer_header_imageView)
        var headerUsername : TextView = headerview!!.findViewById(R.id.drawer_header_username)

        headerImgView.getLayoutParams().width = 250;
        headerImgView.getLayoutParams().height = 250;
        headerImgView.setAdjustViewBounds(true);


            Glide.with(this)
                .load(server.getAvatarReference(userId))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(headerImgView)

        server.getUsernameWithUID(userId,object  : SimpleCallback<String>{
            override fun callback(data: String) {
                headerUsername.text = data
            }
        })
    }

    fun signOut(){
        auth.signOut()
        var intent = Intent(this, LoginActivity::class.java)
        Intent.FLAG_ACTIVITY_NO_HISTORY
        startActivity(intent)
        finish()
    }

    fun goToUpdate(){
        var intent = Intent(this, UpdateProfileActivity::class.java)
        startActivity(intent)
    }

    fun goToMyPosts(){
        var intent = Intent(this, MyPostsActivity::class.java)
        startActivity(intent)
    }

    fun goToPost(){
        var intent = Intent(this, PostActivity::class.java)
        startActivity(intent)
    }

}