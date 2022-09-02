package com.karthik.zohotasknewsapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    var showLoader = MutableLiveData<Boolean>()
    var showToastMsg = MutableLiveData<String>()
}