package com.example.mountainair.Fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.mountainair.Adapters.ActivitiesFragmentAdapter
import com.example.mountainair.Interfaces.SimpleCallback
import com.example.mountainair.R
import com.example.mountainair.Server.Server
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_post.*
import kotlinx.android.synthetic.main.fragment_activities.*


class ActivitiesFragment : Fragment() {
    var list : ArrayList<String> = ArrayList()

    companion object {
        fun newInstance() = ActivitiesFragment()
    }

    private lateinit var viewModel: ActivitiesFragmentViewModel
    private var server : Server = Server()
    private lateinit var activitiesAdapter : ActivitiesFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view : View = inflater.inflate(R.layout.fragment_activities, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ActivitiesFragmentViewModel::class.java)

        server.getActivities(object : SimpleCallback<ArrayList<String>>{
            override fun callback(data: ArrayList<String>) {
                list = data
                activities_recycler.layoutManager = LinearLayoutManager(context)
                activitiesAdapter = ActivitiesFragmentAdapter(context!!, list)
                activities_recycler.adapter = activitiesAdapter
            }
        })
    }

    fun getListOfActivities() : ArrayList<String>{
        return activitiesAdapter.getListOfActivities()
    }
}
