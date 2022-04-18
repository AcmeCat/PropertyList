package com.illtech.propertylist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var propertyDetailsViewModel: PropertyDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        propertyDetailsViewModel = ViewModelProvider(this).get(PropertyDetailsViewModel::class.java)

        propertyDetailsViewModel.selectedProperty.observe(this) {
            supportFragmentManager.beginTransaction().replace(R.id.container, PropertyDetailsFragment.newInstance()).commit()
        }

        if (savedInstanceState == null){

            //refer to PropertyListFragment class
            val fragment = PropertyListFragment.newInstance()

            supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
        }
    }
}