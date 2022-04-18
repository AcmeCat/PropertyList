package com.illtech.propertylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import java.text.NumberFormat

class PropertyDetailsFragment : Fragment () {

    //    works like java static method
    companion object {
        fun newInstance () = PropertyDetailsFragment ()
    }

    private lateinit var propertyDetailsViewModel: PropertyDetailsViewModel
    private lateinit var property: Property

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View {

        var view = inflater.inflate(R.layout.fragment_details, container, false)

        val context = activity as ViewModelStoreOwner
        propertyDetailsViewModel = ViewModelProvider(context).get(PropertyDetailsViewModel::class.java)

        property = propertyDetailsViewModel.selectedProperty.value!!

        val addressText = view?.findViewById<TextView>(R.id.address)
        addressText?.text = property.address

        val priceText = view?.findViewById<TextView>(R.id.price)
        priceText?.text = formatPrice(property.price)

        val agentText = view?.findViewById<TextView>(R.id.agent)
        agentText?.text = property.agent

        val propertyImage = view?.findViewById<ImageView>(R.id.property_details_image)
        val image = property.image
        val resourceId = this.resources.getIdentifier(image, "drawable",
            this.activity?.packageName
        )
        propertyImage?.setImageResource(resourceId)

        return view
    }

    //duplicate method with adaptor
    fun formatPrice(price: Int) : String {
        val moneyFormatter = NumberFormat.getCurrencyInstance()
        moneyFormatter.maximumFractionDigits = 0
        return moneyFormatter.format(price)
    }
}