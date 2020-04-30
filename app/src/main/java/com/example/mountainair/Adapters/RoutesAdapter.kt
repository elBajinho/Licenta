package com.example.mountainair.Adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mountainair.Activities.IndependentRouteActivity
import com.example.mountainair.Activities.ResultedRouteActivity
import com.example.mountainair.Model.Post
import com.example.mountainair.Model.PostToDisplay
import com.example.mountainair.Model.Route
import com.example.mountainair.R
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class RoutesAdapter(val context: Context, val routes : ArrayList<Route>) : RecyclerView.Adapter<RoutesAdapter.ViewHolder>(){

    override fun getItemCount()= routes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.location.text=routes[position].Location
        holder.activities.text = routes[position].Activities
        holder.description.text=routes[position].Description
        holder.level.text = routes[position].Level
        holder.duration.text=routes[position].Time

        var photoRef : StorageReference = Firebase.storage.reference.child("routes").child(routes[position].Image)

        Glide.with(context)
            .load(photoRef)
            .into(holder.photo)

        holder.itemView.setOnClickListener{
            var intent = Intent(context, IndependentRouteActivity :: class.java)
            intent.putExtra("object",routes[position])
            context.startActivity(intent)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.routes_card,parent,false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        val location : TextView = itemView.findViewById(R.id.route_location)
        val description : TextView = itemView.findViewById(R.id.route_description)
        val photo : ImageView = itemView.findViewById(R.id.route_photo)
        val activities : TextView = itemView.findViewById(R.id.route_activities)
        val level : TextView = itemView.findViewById(R.id.route_level)
        var duration : TextView = itemView.findViewById(R.id.route_time)
    }

}