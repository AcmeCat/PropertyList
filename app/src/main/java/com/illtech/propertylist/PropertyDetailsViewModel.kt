package com.illtech.propertylist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
//view model class
class PropertyDetailsViewModel: ViewModel() {
    val selectedProperty = MutableLiveData<Property>()
    val editedProperty = MutableLiveData<Property>()
}