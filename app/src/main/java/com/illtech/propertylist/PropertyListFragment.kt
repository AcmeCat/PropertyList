package com.illtech.propertylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
//list fragment controller class
class PropertyListFragment : Fragment() {

    //works like java static method
    companion object {
        fun newInstance () = PropertyListFragment ()
    }

    //class variables
    private var propertyArray: ArrayList<Property> = ArrayList ()
    private lateinit var propertyDetailsViewModel: PropertyDetailsViewModel

    //loads initial property data. gets reference to view model
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        propertyArray.add(Property("1", "house1", "7 Newhaven Street, Mount Tarcoola, 6530", 350000, "John Sudz"))
        propertyArray.add(Property("2", "house2", "32B Mark Way, Mount Tarcoola, 6530", 180000, "Chelsea Wiles"))
        propertyArray.add(Property("3", "house3", "2/138 Fitzgerald Street, Beachlands, 6530", 325000, "Marie Peters"))
        propertyArray.add(Property("4", "house4", "13 Eastbourne Parade, Sunset Beach, 6530", 285000, "Pip Allen"))
        propertyArray.add(Property("5", "house5", "58 Thomas Street, West Perth, 6005", 585000, "Carl Phathead"))
        propertyArray.add(Property("6", "house6", "1/34 Thomas Street, West Perth, 6005", 760000, "Don Wan Sills"))

        val context = activity as ViewModelStoreOwner
        propertyDetailsViewModel = ViewModelProvider(context).get(PropertyDetailsViewModel::class.java)
    }

    //loads recycler view and components
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val recyclerView = inflater.inflate(R.layout.fragment_list, container, false) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        //updated list from editedproperty live data model in view model
        val editedProperty = propertyDetailsViewModel.editedProperty.value
        if (editedProperty != null) {
            for (i in 0 until (propertyArray.size-1)){
                if (propertyArray[i].id ==editedProperty.id){
                    propertyArray[i] = editedProperty
                    break
                }
            }
        }
        //assigns loaded adaptor
        recyclerView.adapter = PropertyAdapter(propertyArray)
        return recyclerView
    }
}