package com.example.mountainair.Fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.mountainair.Interfaces.SimpleCallback
import com.example.mountainair.Model.GeographicSelection

import com.example.mountainair.R
import com.example.mountainair.Server.Service
import kotlinx.android.synthetic.main.geographic_fragment.*

class GeographicFragment : Fragment() {

    var service : Service = Service()
    var judeteList : ArrayList<String> = ArrayList()
    var peaksList : ArrayList<String> = ArrayList()
    var mountainList : ArrayList<String> = ArrayList()
    var carphatsList : ArrayList<String> = ArrayList()
    companion object {
        fun newInstance() = GeographicFragment()
    }

    private lateinit var viewModel: GeographicFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.geographic_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(GeographicFragmentViewModel::class.java)

        setCharpatsSpinner()
        setMountainsSpinner()
        setPeaksSpinner()
        setJudeteSpinner("")

        spinner_carphatic_region?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position!=0) {
                    setMountainsSpinner(carphatsList[position])
                }
                else {
                    setMountainsSpinner()

                }
            }
        }

        spinner_mountains?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position!=0)
                    setPeaksSpinner(mountainList[position])
                else
                    setPeaksSpinner()
            }
        }

        spinner_peak?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position!=0)
                    setJudeteSpinner(peaksList[position])
                else
                    setJudeteSpinner()
            }
        }
    }

    private fun setJudeteSpinner(peaks :String = "") {
        judeteList.add("oricare")

        service.getJudete(object : SimpleCallback<ArrayList<String>>{
            override fun callback(data: ArrayList<String>) {
                judeteList = data
                val adapter = ArrayAdapter<String>(
                    context!!,
                    android.R.layout.simple_spinner_item,
                    judeteList
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner_judete.adapter = adapter
            }
        }, peaks)
    }

    private fun setPeaksSpinner(mountains: String= "") {
        peaksList.add("oricare")


        service.getPeaks(object : SimpleCallback<ArrayList<String>>{
            override fun callback(data: ArrayList<String>) {
                peaksList = data
                val adapter = ArrayAdapter<String>(
                    context!!,
                    android.R.layout.simple_spinner_item,
                    peaksList
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner_peak.adapter = adapter
            }
        }, mountains)
    }

    private fun setMountainsSpinner(charpats : String = "" ) {
        mountainList.add("oricare")
        service.getMountains(object : SimpleCallback<ArrayList<String>>{
            override fun callback(data: ArrayList<String>) {
                mountainList = data
                val adapter = ArrayAdapter<String>(
                    context!!,
                    android.R.layout.simple_spinner_item,
                    mountainList
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner_mountains.adapter = adapter
            }
        }, charpats)
    }

    private fun setCharpatsSpinner() {
        carphatsList.add("oricare")
        service.getCharpats(object : SimpleCallback<ArrayList<String>>{
            override fun callback(data: ArrayList<String>) {
                carphatsList = data
                val adapter = ArrayAdapter<String>(
                    context!!,
                    android.R.layout.simple_spinner_item,
                    carphatsList
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner_carphatic_region.adapter = adapter
            }
        })
    }



    fun getGeographicSelection() : GeographicSelection{
        return GeographicSelection(
            spinner_carphatic_region.selectedItem.toString(),
            spinner_mountains.selectedItem.toString(),
            spinner_peak.selectedItem.toString(),
            spinner_judete.selectedItem.toString())
    }

}
