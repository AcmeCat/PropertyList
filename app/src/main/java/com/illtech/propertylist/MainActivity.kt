package com.illtech.propertylist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    //reference to view model
    private lateinit var propertyDetailsViewModel: PropertyDetailsViewModel

    //loads main activity xml file into view. builds view model. adds observers to view model live data objects. loads property list into view (if no saved instance state)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        propertyDetailsViewModel = ViewModelProvider(this).get(PropertyDetailsViewModel::class.java) //view model

        //loads property detail into view and stacks the list fragment for later. bound to live data object in view model
        propertyDetailsViewModel.selectedProperty.observe(this) {
            supportFragmentManager.beginTransaction().replace(R.id.container, PropertyDetailsFragment.newInstance()).addToBackStack("list_fragment").commit()
        }

        //pops list fragment back into view. bound to live data object in view model
        propertyDetailsViewModel.editedProperty.observe(this) {
            supportFragmentManager.popBackStack()
        }

        if (savedInstanceState == null){
            loadFragment(PropertyListFragment.newInstance()) //list view loaded (unless saved instance)
        }
    }

    //loads fragment into container view
    private fun loadFragment (fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }

    //manages back button in bottom control bar. exits app from list view. returns to list from details (via dialogue)
    override fun onBackPressed() {
        val currentFragment: Fragment? = supportFragmentManager.findFragmentById(R.id.container)

        if (currentFragment != null && currentFragment.view?.id == R.id.property_details_fragment) {
            val detailsFragment = currentFragment as PropertyDetailsFragment
            detailsFragment.checkIfDone(true)//see implementation in PropertyListFragment class
        } else {
            finish()
        }
    }
}