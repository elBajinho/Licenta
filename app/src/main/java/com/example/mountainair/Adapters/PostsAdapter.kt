package com.example.mountainair.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mountainair.Model.Post
import com.example.mountainair.R
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class PostsAdapter(val context: Context, val posts : ArrayList<Post>) : RecyclerView.Adapter<PostsAdapter.ViewHolder>(){

    override fun getItemCount()= posts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.username.text=posts[position].username
        holder.description.text=posts[position].description
        Picasso.with(context).load(posts[position].photo).into(holder.photo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.post_row,parent,false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        val username : TextView = itemView.findViewById(R.id.post_row_username)
        val description : TextView = itemView.findViewById(R.id.post_row_description_id)
        val photo : ImageView = itemView.findViewById(R.id.imageId)
    }

}