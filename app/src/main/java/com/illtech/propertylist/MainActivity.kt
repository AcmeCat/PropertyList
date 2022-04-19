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
            loadFragment(PropertyDetailsFragment.newInstance())
        }

        propertyDetailsViewModel.editedProperty.observe(this) {
            loadFragment(PropertyListFragment.newInstance())
        }

        if (savedInstanceState == null){
            loadFragment(PropertyListFragment.newInstance())
        }
    }

    fun loadFragment (fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }
}