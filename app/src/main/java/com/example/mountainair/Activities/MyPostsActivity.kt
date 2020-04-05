package com.example.mountainair.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mountainair.Adapters.MyPostsAdapter
import com.example.mountainair.Adapters.PostsAdapter
import com.example.mountainair.Model.Post
import com.example.mountainair.R
import kotlinx.android.synthetic.main.activity_my_posts.*
import kotlinx.android.synthetic.main.fedd_main.*

class MyPostsActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_posts)

        var posts : ArrayList<Post> = ArrayList()
        for(i in 0..100){
            posts.add(Post("Vlad","muntii  astia th0", "https://picsum.photos/600/300?random&"+i))
        }

        myPostsRecyclerView.layoutManager = LinearLayoutManager(this)
        myPostsRecyclerView.adapter = MyPostsAdapter(this, posts)
    }
}