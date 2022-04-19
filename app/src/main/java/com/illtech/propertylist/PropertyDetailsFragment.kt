package com.illtech.propertylist

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import java.lang.StringBuilder
import java.text.NumberFormat

class PropertyDetailsFragment : Fragment () {

    //    works like java static method
    companion object {
        fun newInstance () = PropertyDetailsFragment ()
    }

    private lateinit var propertyDetailsViewModel: PropertyDetailsViewModel
    private lateinit var property: Property
    private lateinit var addressView: TextView
    private lateinit var priceView: TextView
    private lateinit var agentView: TextView

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View {

        var view = inflater.inflate(R.layout.fragment_details, container, false)

        val context = activity as ViewModelStoreOwner
        propertyDetailsViewModel = ViewModelProvider(context).get(PropertyDetailsViewModel::class.java)

        property = propertyDetailsViewModel.selectedProperty.value!!

        if (view != null){
            addressView = view.findViewById<TextView>(R.id.address)
            priceView = view.findViewById<TextView>(R.id.price)
            agentView = view.findViewById<TextView>(R.id.agent)
        }

        //init text in views
        addressView.text = property.address
        priceView.text = formatPrice(property.price)
        agentView.text = property.agent

        //load image of property
        val propertyImage = view?.findViewById<ImageView>(R.id.property_details_image)
        val image = property.image
        val resourceId = this.resources.getIdentifier(image, "drawable",
            this.activity?.packageName
        )
        propertyImage?.setImageResource(resourceId)


        val doneButton = view?.findViewById<Button>(R.id.done)

        doneButton?.setOnClickListener {_ ->

            checkIfDone(false)
            println("button")
        }

        return view
    }

    //duplicate method with adaptor
    private fun formatPrice(price: Int) : String {
        return "$$price"
    }

    private fun formatPrice(price: String) : Int? {
        val priceString = price.drop(1)
        return if (priceString.isDigitsOnly()) {
            priceString.toInt()
        } else {
            null
        }
    }

    private fun updateModel (makeChanges: Boolean) {
        if (makeChanges){
            property.address = addressView.text.toString()
            property.price = formatPrice(priceView.text.toString())!!
            property.agent = agentView.text.toString()
        }
        propertyDetailsViewModel.editedProperty.value = property
    }

    private fun detailsChanged() : Boolean {

        return property.address != addressView.text.toString() ||
        property.price != formatPrice(priceView.text.toString()) ||
        property.agent != agentView.text.toString()
    }

    private fun allFieldsValid () : Boolean {
        return addressView.text.toString() != "" &&
                priceView.text.toString() != "" &&
                agentView.text.toString() != "" &&
                formatPrice(priceView.text.toString()) != null
    }

    private fun showInvalidDialog(){
        val dialogBuilder = AlertDialog.Builder(requireActivity()).setMessage("Please make sure that no fields are empty and a valid number is entered for price.")
            .setPositiveButton("OK", null)
        val alert = dialogBuilder.create()
        alert.setTitle("Hold Up!")
        alert.show()

    }

    private fun showBackDialog(){
        val dialogBuilder = AlertDialog.Builder(requireActivity()).setMessage("Are you sure you want to discard your changes?")
            .setPositiveButton("OK") { _, _ -> updateModel(false)}
            .setNegativeButton("Cancel",null)
        val alert = dialogBuilder.create()
        alert.setTitle("Wait a second!")
        alert.show()

    }

    fun checkIfDone (backPressed: Boolean){
        if (backPressed) {
            if (detailsChanged()) {
                showBackDialog()
            } else {
                updateModel(false)
            }
        } else {
            if (detailsChanged()) {
                if (allFieldsValid()) {
                    updateModel(true)
                } else {
                    showInvalidDialog()
                }
            } else {
                updateModel(false)
            }
        }
    }
}