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
//detail view controller class
class PropertyDetailsFragment : Fragment () {

    //works like java static method
    companion object {
        fun newInstance () = PropertyDetailsFragment ()
    }

    //class variables for view model, property in current view, and text field references
    private lateinit var propertyDetailsViewModel: PropertyDetailsViewModel
    private lateinit var property: Property
    private lateinit var addressView: TextView
    private lateinit var priceView: TextView
    private lateinit var agentView: TextView

    //inflates details view. gets reference to view model. gets value of selected property. gets reference to text fields.
    // populates details text. loads property image. gets reference to done button and applies listener
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
        doneButton?.setOnClickListener {_ -> checkIfDone(false)} //validates and prompts. see implementation below

        return view
    }

    //formats price as string
    private fun formatPrice(price: Int) : String {
        return "$$price"
    }

    //converts price string to optional Int
    private fun formatPrice(price: String) : Int? {
        val priceString = price.drop(1)
        return if (priceString.isDigitsOnly()) {
            priceString.toInt()
        } else {
            null
        }
    }

    //updates editedproperty live data object in view model
    private fun updateModel (makeChanges: Boolean) {
        if (makeChanges){
            property.address = addressView.text.toString()
            property.price = formatPrice(priceView.text.toString())!!
            property.agent = agentView.text.toString()
        }
        propertyDetailsViewModel.editedProperty.value = property
    }

    //returns false if all current text field values match values in model
    private fun detailsChanged() : Boolean {
        return property.address != addressView.text.toString() ||
        property.price != formatPrice(priceView.text.toString()) ||
        property.agent != agentView.text.toString()
    }

    //returns true if all text fields contain valid data
    private fun allFieldsValid () : Boolean {
        return addressView.text.toString() != "" &&
                priceView.text.toString() != "" &&
                agentView.text.toString() != "" &&
                formatPrice(priceView.text.toString()) != null
    }

    //displays dialog about invalid text field inputs
    private fun showInvalidDialog(){
        val dialogBuilder = AlertDialog.Builder(requireActivity()).setMessage("Please make sure that no fields are empty and a valid number is entered for price.")
            .setPositiveButton("OK", null)
        val alert = dialogBuilder.create()
        alert.setTitle("Hold Up!")
        alert.show()

    }

    //shows dialog confirming exit without committing edited data
    private fun showBackDialog(){
        val dialogBuilder = AlertDialog.Builder(requireActivity()).setMessage("Are you sure you want to discard your changes?")
            .setPositiveButton("OK") { _, _ -> updateModel(false)}
            .setNegativeButton("Cancel",null)
        val alert = dialogBuilder.create()
        alert.setTitle("Wait a second!")
        alert.show()

    }

    //shows dialogs/updates model/and manages navigation back to list view (via changes to live data objects in view model
    // - see back buttons onclick implementation in main activity class
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