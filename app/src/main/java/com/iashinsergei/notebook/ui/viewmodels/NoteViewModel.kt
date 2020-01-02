package com.iashinsergei.notebook.ui.viewmodels

import com.iashinsergei.notebook.data.NoteResult
import com.iashinsergei.notebook.data.NotesRepository
import com.iashinsergei.notebook.data.entity.Note
import com.iashinsergei.notebook.ui.viewstates.NoteViewState

class NoteViewModel : BaseViewModel<Note?, NoteViewState>() {

    init {
        viewStateLiveData.value = NoteViewState()
    }

    private var pendingNote: Note? = null

    fun save(note: Note) {
        pendingNote = note
    }

    override fun onCleared() {
        pendingNote?.let {
            NotesRepository.saveNote(it)
        }
    }

    fun loadNote(id: String) {
        NotesRepository.getNoteById(id).observeForever {
            it?: return@observeForever
            when(it) {
                is NoteResult.Success<*> -> viewStateLiveData.value = NoteViewState(note = it.data as? Note)
                is NoteResult.Error -> viewStateLiveData.value = NoteViewState(error = it.error)
            }
        }
    }
}