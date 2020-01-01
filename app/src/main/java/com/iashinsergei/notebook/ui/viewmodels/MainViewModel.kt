package com.iashinsergei.notebook.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iashinsergei.notebook.data.NotesRepository
import com.iashinsergei.notebook.ui.viewstates.MainViewState

class MainViewModel : ViewModel() {

    private val viewStateLiveData : MutableLiveData<MainViewState> = MutableLiveData()

    init {
        viewStateLiveData.value = MainViewState(NotesRepository.getNotes())
    }

    fun viewState(): LiveData<MainViewState> = viewStateLiveData

}