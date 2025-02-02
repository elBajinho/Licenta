package com.example.mountainair.Fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mountainair.Adapters.ActivitiesFragmentAdapter
import com.example.mountainair.Interfaces.SimpleCallback
import com.example.mountainair.R
import com.example.mountainair.Server.Service
import kotlinx.android.synthetic.main.fragment_activities.*


class ActivitiesFragment : Fragment() {
    var list : ArrayList<String> = ArrayList()

    companion object {
        fun newInstance() = ActivitiesFragment()
    }

    private lateinit var viewModel: ActivitiesFragmentViewModel
    private var service : Service = Service()
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

        service.getActivities(object : SimpleCallback<ArrayList<String>>{
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
