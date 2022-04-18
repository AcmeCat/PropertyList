package com.illtech.propertylist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PropertyDetailsViewModel: ViewModel() {
    val selectedProperty = MutableLiveData<Property>()
}