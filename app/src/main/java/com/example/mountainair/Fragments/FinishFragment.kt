package com.example.mountainair.Fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mountainair.Activities.DoYourJurneyActivity

import com.example.mountainair.R
import kotlinx.android.synthetic.main.finish_fragment.*

class FinishFragment : Fragment() {

    companion object {
        fun newInstance() = FinishFragment()
    }

    private lateinit var viewModel: FinishFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.finish_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FinishFragmentViewModel::class.java)

        finish_search_button.setOnClickListener {
            (activity as DoYourJurneyActivity).goTo()
        }
    }

}
