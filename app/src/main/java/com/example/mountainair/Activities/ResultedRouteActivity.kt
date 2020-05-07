package com.example.mountainair.Activities

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mountainair.Adapters.RoutesAdapter
import com.example.mountainair.Interfaces.SimpleCallback
import com.example.mountainair.Model.Filters
import com.example.mountainair.Model.Route
import com.example.mountainair.R
import com.example.mountainair.Server.Server
import kotlinx.android.synthetic.main.activity_resulted_routes.*
import javax.security.auth.callback.Callback

class ResultedRouteActivity :AppCompatActivity(){

    private var server : Server = Server()
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

        server.getRoutes(filter ,object : SimpleCallback<ArrayList<Route>> {
            override fun callback(data: ArrayList<Route>) {
                var list = data
                var adapter = RoutesAdapter(context, list)
                resulted_route_recycler.layoutManager = LinearLayoutManager(context)

                resulted_route_recycler.adapter = adapter
                Log.i("lista: ", list.toString())
            }
        })
    }
}