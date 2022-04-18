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

        propertyArray.add(Property("1", "house1.jpg", "7 Newhaven Street, Mount Tarcoola, 6530", 350000, "John Sudz"))
        propertyArray.add(Property("2", "house1.jpg", "32B Mark Way, Mount Tarcoola, 6530", 180000, "Chelsea Wiles"))
        propertyArray.add(Property("3", "house1.jpg", "2/138 Fitzgerald Street, Beachlands, 6530", 325000, "Marie Peters"))
        propertyArray.add(Property("4", "house1.jpg", "13 Eastbourne Parade, Sunset Beach, 6530", 285000, "Pip Allen"))
        propertyArray.add(Property("5", "house1.jpg", "58 Thomas Street, West Perth, 6005", 585000, "Carl Phathead"))
        propertyArray.add(Property("6", "house1.jpg", "1/34 Thomas Street, West Perth, 6005", 760000, "Don Wan Sills"))
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