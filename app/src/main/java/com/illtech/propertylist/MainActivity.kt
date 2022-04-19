package com.illtech.propertylist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var propertyDetailsViewModel: PropertyDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        propertyDetailsViewModel = ViewModelProvider(this).get(PropertyDetailsViewModel::class.java)

        propertyDetailsViewModel.selectedProperty.observe(this) {
            supportFragmentManager.beginTransaction().replace(R.id.container, PropertyDetailsFragment.newInstance()).addToBackStack("list_fragment").commit()
        }

        propertyDetailsViewModel.editedProperty.observe(this) {
            supportFragmentManager.popBackStack()
        }

        if (savedInstanceState == null){
            loadFragment(PropertyListFragment.newInstance())
        }
    }

    private fun loadFragment (fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }

    override fun onBackPressed() {
        val currentFragment: Fragment? = supportFragmentManager.findFragmentById(R.id.container)

        if (currentFragment != null && currentFragment.view?.id == R.id.property_details_fragment) {
            val detailsFragment = currentFragment as PropertyDetailsFragment
            detailsFragment.checkIfDone(true)
        } else {
            finish()
        }
    }
}