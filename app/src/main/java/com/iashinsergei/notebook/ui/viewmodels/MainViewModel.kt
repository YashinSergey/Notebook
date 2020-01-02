package com.iashinsergei.notebook.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.iashinsergei.notebook.data.NoteResult
import com.iashinsergei.notebook.data.NotesRepository
import com.iashinsergei.notebook.data.entity.Note
import com.iashinsergei.notebook.ui.viewstates.MainViewState

class MainViewModel : BaseViewModel<List<Note>?, MainViewState>() {

    @Suppress("UNCHECKED_CAST")
    private val notesObserver  = Observer<NoteResult> {
       it ?: return@Observer
        when(it) {
            is NoteResult.Success<*> -> {viewStateLiveData.value = MainViewState(notes = it.data as? List<Note>) }
            is NoteResult.Error -> {viewStateLiveData.value = MainViewState(error = it.error)}
        }
    }

    private val repositoryNotes = NotesRepository.getNotes()

    init {
        viewStateLiveData.value = MainViewState()
        repositoryNotes.observeForever(notesObserver)
    }

    fun viewState(): LiveData<MainViewState> = viewStateLiveData

    override fun onCleared() {
        repositoryNotes.removeObserver(notesObserver)
        super.onCleared()
    }
}