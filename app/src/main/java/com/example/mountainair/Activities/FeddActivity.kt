package com.example.mountainair.Activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mountainair.Adapters.PostsAdapter
import com.example.mountainair.Model.Post
import com.example.mountainair.R
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.android.synthetic.main.fedd_main.*

class FeddActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        auth = FirebaseAuth.getInstance()

        var posts : ArrayList<Post> = ArrayList()
        for(i in 0..100){
            posts.add(Post("Vlad","muntii  astia th0", "https://picsum.photos/600/300?random&"+i))
        }

        recyclerFeed.layoutManager = LinearLayoutManager(this)
        recyclerFeed.adapter = PostsAdapter(this, posts)


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