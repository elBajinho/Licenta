package com.example.mountainair.Activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mountainair.Adapters.RoutesAdapter
import com.example.mountainair.Interfaces.SimpleCallback
import com.example.mountainair.Model.Filters
import com.example.mountainair.Model.Route
import com.example.mountainair.R
import com.example.mountainair.Server.Service
import kotlinx.android.synthetic.main.activity_resulted_routes.*

class ResultedRouteActivity :AppCompatActivity(){

    private var service : Service = Service()
    private var context = this


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resulted_routes)

        var filter : Filters = intent.getParcelableExtra("filters")

       // Toast.makeText(this,filter.activities.toString(),Toast.LENGTH_SHORT).show()
       // Toast.makeText(this,filter.date.toString(),Toast.LENGTH_SHORT).show()
       // Toast.makeText(this,filter.gs.toString(),Toast.LENGTH_SHORT).show()
       // Toast.makeText(this,filter.rs.toString(),Toast.LENGTH_SHORT).show()
       // Toast.makeText(this,filter.ws.toString(),Toast.LENGTH_SHORT).show()

        service.getRoutes(filter ,object : SimpleCallback<ArrayList<Route>> {
            override fun callback(data: ArrayList<Route>) {
                var list = data
                var adapter = RoutesAdapter(context, list, filter.date)
                resulted_route_recycler.layoutManager = LinearLayoutManager(context)

                resulted_route_recycler.adapter = adapter
                Log.i("lista: ", list.toString())
            }
        })
    }
}