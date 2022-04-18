package com.illtech.propertylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PropertyListFragment : Fragment() {

    companion object {
        fun newInstance () = PropertyListFragment ()
    }

    val propertyArray: ArrayList<Property> = ArrayList ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        propertyArray.add(Property("1", "Pebbles", "Shepherd", 3, "John"))
        propertyArray.add(Property("2", "Bones", "Labrador", 5, "John"))
        propertyArray.add(Property("3", "Daisy", "Husky", 2, "John"))
        propertyArray.add(Property("4", "Patch", "Bulldog", 4, "John"))
        propertyArray.add(Property("5", "Lady", "Collie", 10, "John"))
        propertyArray.add(Property("6", "Butthead", "Mastiff", 7, "John"))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val recyclerView = inflater.inflate(R.layout.fragment_list, container, false) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = PropertyAdapter(propertyArray)
        return recyclerView
    }
}