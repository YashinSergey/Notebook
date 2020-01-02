package com.iashinsergei.notebook.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iashinsergei.notebook.data.NotesRepository
import com.iashinsergei.notebook.ui.viewstates.MainViewState

class MainViewModel : ViewModel() {

    private val viewStateLiveData : MutableLiveData<MainViewState> = MutableLiveData()

    init {
        NotesRepository.getNotes().observeForever{notes ->
            notes?.let { viewStateLiveData.value = viewStateLiveData.value?.copy(notes = it)
                ?: MainViewState(it) }
        }
    }

    fun viewState(): LiveData<MainViewState> = viewStateLiveData

}