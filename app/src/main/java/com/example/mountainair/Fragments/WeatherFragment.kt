package com.example.mountainair.Fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.example.mountainair.Model.WheatherSelection

import com.example.mountainair.R
import kotlinx.android.synthetic.main.weather_fragment.*

class WeatherFragment : Fragment() {

    companion object {
        fun newInstance() = WeatherFragment()
    }

    private lateinit var viewModel: WeatherFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(WeatherFragmentViewModel::class.java)
        initialiseSeeker()
    }

    private fun initialiseSeeker() {
        seekBar_wheather_minWind.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                fragment_wheather_wind_min_text.text = "Minimum wind speed: $i km/h"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })

        seekBar_wheather_maxWind.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                fragment_wheater_wind_max_text.text="Maximum wind speed:  $i km/h"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })

        seekbar_wheater_temp_min.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                fragment_wheater_temp_min_text.text = "Minimum temperatureȘ $i C"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })

        seekbar_wheater_temp_max.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                fragment_wheater_temp_max_text.text="Maximum temperature:  $i C"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
    }

    fun getWheatherSelection() : WheatherSelection {
        var rain : Boolean = false
        var foog : Boolean = false
        if(rainYes.isChecked){
            rain = true
        }

        if(foogYes.isChecked){
            foog= true
        }

        return WheatherSelection(
            seekBar_wheather_minWind.progress,
            seekBar_wheather_maxWind.progress,
            seekbar_wheater_temp_min.progress,
            seekbar_wheater_temp_max.progress,
            rain, foog
        )

    }
}
