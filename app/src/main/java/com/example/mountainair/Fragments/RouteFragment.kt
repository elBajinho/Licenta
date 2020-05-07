package com.example.mountainair.Fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import com.example.mountainair.Model.RouteSelection

import com.example.mountainair.R
import kotlinx.android.synthetic.main.route_fragment.*

class RouteFragment : Fragment() {

    companion object {
        fun newInstance() = RouteFragment()
    }

    private lateinit var viewModel: RouteFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.route_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RouteFragmentViewModel::class.java)
        initialiseSeekbars()
    }

    private fun initialiseSeekbars() {
        seekBar_route_minHours.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                min_hours.text = "$i ore"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Do something
                Toast.makeText(context!!,"start tracking",Toast.LENGTH_SHORT).show()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Do something
                Toast.makeText(context!!,"stop tracking",Toast.LENGTH_SHORT).show()
            }
        })

        seekBar_route_maxHours.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                max_hours.text = "$i ore"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Do something
                Toast.makeText(context!!,"start tracking",Toast.LENGTH_SHORT).show()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Do something
                Toast.makeText(context!!,"stop tracking",Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun geRouteSelection() : RouteSelection{

        var listOfDificulties : ArrayList<String> = ArrayList()

        if(easy_check.isChecked){
            listOfDificulties.add("Usor")
        }

        if(medium_check.isChecked){
            listOfDificulties.add("Mediu")
        }

        if(hard_check.isChecked){
            listOfDificulties.add("Ridicata")
        }

        return RouteSelection(seekBar_route_minHours.progress, seekBar_route_maxHours.progress,listOfDificulties)
    }

}
