package com.example.mountainair.Adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mountainair.R
import kotlinx.android.synthetic.main.activities_card.view.*

class ActivitiesFragmentAdapter (val context: Context, val activities: ArrayList<String>) : RecyclerView.Adapter<ActivitiesFragmentAdapter.MyViewHolder>(){
    private var listOfActivities : ArrayList<String> = ArrayList()

    fun getListOfActivities() : ArrayList<String>{
        return listOfActivities
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.activities_card,parent, false)
        return MyViewHolder(view);
    }

    override fun getItemCount(): Int {
        return activities.size;
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val mountain = activities[position]
        holder.setData(mountain, position)



        holder.itemView.setOnClickListener{

            if(!listOfActivities.contains(activities[position])){
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.colorPrimary))
                holder.itemView.checkButton.setColorFilter(context.resources.getColor(R.color.colorPrimary))
                listOfActivities.add(activities[position])
                Toast.makeText(context,activities[position]+ "a fost adaugat",Toast.LENGTH_SHORT).show();
            }
            else{
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
                holder.itemView.checkButton.setColorFilter(context.resources.getColor(R.color.white))
                listOfActivities.remove(activities[position])
                Toast.makeText(context,activities[position]+ "a fost sters",Toast.LENGTH_SHORT).show();
            }
            notifyDataSetChanged()
        }
    }

    inner class MyViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){

        var currentActivity : String? = null;
        var currentPosition : Int = 0;

        fun setData(activity: String?, pos : Int){
            activity?.let{
                itemView.activity_text.text=activity;
            }
            this.currentActivity=activity;
            this.currentPosition=pos;
        }
    }
}