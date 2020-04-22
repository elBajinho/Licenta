package com.example.mountainair.Fragments

import android.app.DatePickerDialog
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast

import com.example.mountainair.R
import kotlinx.android.synthetic.main.date_fragment.*
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

class DateFragment : Fragment() {

    companion object {
        fun newInstance() = DateFragment()
    }

    private lateinit var viewModel: DateFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.date_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DateFragmentViewModel::class.java)
        // TODO: Use the ViewModel
        val today = Calendar.getInstance()
        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        ) { view, year, month, day ->
            val month=month+1
            val msg = "You Selected: $day/$month/$year"
            Toast.makeText(context!! , msg, Toast.LENGTH_SHORT).show()
        }
    }

//    fun getDate(): Date{
//
//        val today = Calendar.getInstance()
//        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
//            today.get(Calendar.DAY_OF_MONTH)
//        ) { view, year, month, day ->
//            val msg = "You Selected: $day/$month/$year"
//            Toast.makeText(context!! , msg, Toast.LENGTH_SHORT).show()
//        }
//    }

}
