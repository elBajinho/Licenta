package com.example.mountainair.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mountainair.Model.Post
import com.example.mountainair.Model.PostToDisplay
import com.example.mountainair.R
import com.example.mountainair.Server.Server
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.my_post_row.view.*

class MyPostsAdapter(val context: Context, val posts : ArrayList<PostToDisplay>) : RecyclerView.Adapter<MyPostsAdapter.ViewHolder>(){

    val server = Server()

    override fun getItemCount()= posts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.username.text=posts[position].userName
        holder.description.hint=posts[position].description
        //Picasso.with(context).load(posts[position].photo).into(holder.photo)
        Glide.with(context)
            .load(posts[position].photoRef)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(holder.photo)

        holder.setData(posts[position],position)

        holder.itemView.my_post_delete.setOnClickListener {
            server.deletePost(posts[position].photo, posts[position].userId)
            holder.itemView.visibility = View.INVISIBLE
            update_afterDelete(posts[position])
        }

        holder.itemView.my_post_save.setOnClickListener {
            server.updatePost(posts[position].photo,posts[position].userId, holder.description.text.toString())
            update_afterUpdate(position,holder.description.text.toString())
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.my_post_row,parent,false)
        return ViewHolder(view)
    }

     fun update_afterDelete(post: PostToDisplay){
        val position = posts.indexOf(post)
        posts.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, posts.size)
    }

    fun update_afterUpdate(position: Int, desc: String){
        posts[position].description=desc
        notifyItemChanged(position)
    }



    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        val username : TextView = itemView.findViewById(R.id.my_post_row_username)
        val description : EditText = itemView.findViewById(R.id.my_post_row_description_id)
        val photo : ImageView = itemView.findViewById(R.id.my_post_imageId)
        var currentPost : PostToDisplay? = null
        var currentPosition: Int =0

        fun setData(post : PostToDisplay, pos : Int){
            currentPost = post
            currentPosition = pos
        }
    }

}