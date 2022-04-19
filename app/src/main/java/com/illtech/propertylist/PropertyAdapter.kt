package com.illtech.propertylist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
//recyclerview adapter
class PropertyAdapter (var properties: List<Property>): RecyclerView.Adapter<PropertyAdapter.ViewHolder> () {

    //reference to view model
    private lateinit var propertyDetailsViewModel: PropertyDetailsViewModel

    //builds the viewholder. inits reference to view model.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)

        val viewModelStoreOwner = parent.context as ViewModelStoreOwner
        propertyDetailsViewModel = ViewModelProvider(viewModelStoreOwner).get(PropertyDetailsViewModel::class.java)

        return ViewHolder(view)
    }

    //helper to get count of list items
    override fun getItemCount() = properties.size

    //binds property to view holder
    override fun onBindViewHolder(propertyHolder: ViewHolder, position: Int) {
        val property = properties[position]
        propertyHolder.bind(property)
    }


    inner class ViewHolder (val view: View): RecyclerView.ViewHolder(view), View.OnClickListener {

        //ref to this viewholders property object
        lateinit var property: Property

        //makes list item clickable (for navigation to details)
        init {
            itemView.setOnClickListener(this)
        }

        //binds the property objects properties (:/) to the address and price views of the list fragment
        fun bind (property: Property) {
            this.property = property

            val addressView: TextView = view.findViewById(R.id.address)
            val priceView: TextView = view.findViewById(R.id.price)

            addressView.text = property.address
            priceView.text = formatPrice(property.price)
        }

        //updates view models selectedproperty live data object to reflect the list item clicked
        override fun onClick(v: View) {
            propertyDetailsViewModel.selectedProperty.value = property
        }

        //formats models price integer to string in currency format eg 123: Int -> "$123": String
        private fun formatPrice(price: Int) : String {
            return "$$price"
        }

    }
}