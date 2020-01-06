package com.sergeiiashin.notebook.ui.viewmodels

import com.sergeiiashin.notebook.data.NoteResult
import com.sergeiiashin.notebook.data.NotesRepository
import com.sergeiiashin.notebook.data.entity.Note
import com.sergeiiashin.notebook.ui.viewstates.NoteViewState

class NoteViewModel(private val notesRepository: NotesRepository) : BaseViewModel<Note?, NoteViewState>() {

    init {
        viewStateLiveData.value = NoteViewState()
    }

    private var pendingNote: Note? = null

    fun save(note: Note) {
        pendingNote = note
    }

    override fun onCleared() {
        pendingNote?.let {
            notesRepository.saveNote(it)
        }
    }

    fun loadNote(id: String) {
        notesRepository.getNoteById(id).observeForever {
            it?: return@observeForever
            when(it) {
                is NoteResult.Success<*> -> viewStateLiveData.value = NoteViewState(note = it.data as? Note)
                is NoteResult.Error -> viewStateLiveData.value = NoteViewState(error = it.error)
            }
        }
    }
}