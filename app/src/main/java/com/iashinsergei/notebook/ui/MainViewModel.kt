package com.iashinsergei.notebook.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val viewStateLiveData : MutableLiveData<String> = MutableLiveData()

    init {
        viewStateLiveData.value = "Testing"
    }

    fun viewState(): LiveData<String> = viewStateLiveData

}