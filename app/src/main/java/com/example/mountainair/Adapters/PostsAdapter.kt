package com.example.mountainair.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mountainair.Model.Post
import com.example.mountainair.Model.PostToDisplay
import com.example.mountainair.R
import com.squareup.picasso.Picasso

class PostsAdapter(val context: Context, val posts : ArrayList<PostToDisplay>) : RecyclerView.Adapter<PostsAdapter.ViewHolder>(){

    override fun getItemCount()= posts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.username.text=posts[position].userName
        holder.description.text=posts[position].description
        //Picasso.with(context).load(posts[position].photo).into(holder.photo)
        Glide.with(context)
            .load(posts[position].photo)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(holder.photo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.fedd_post_row,parent,false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        val username : TextView = itemView.findViewById(R.id.post_row_username)
        val description : TextView = itemView.findViewById(R.id.post_row_description_id)
        val photo : ImageView = itemView.findViewById(R.id.imageId)
    }

}