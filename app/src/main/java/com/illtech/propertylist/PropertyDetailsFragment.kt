package com.illtech.propertylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class PropertyDetailsFragment : Fragment () {

    //    works like java static method
    companion object {
        fun newInstance () = PropertyDetailsFragment ()
    }

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View {

        var view = inflater.inflate(R.layout.fragment_details, container, false)

        return view
    }
}