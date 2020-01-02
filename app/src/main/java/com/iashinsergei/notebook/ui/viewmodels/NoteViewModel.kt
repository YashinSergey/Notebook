package com.iashinsergei.notebook.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.iashinsergei.notebook.data.NotesRepository
import com.iashinsergei.notebook.data.entity.Note

class NoteViewModel : ViewModel() {

    private var pendingNote: Note? = null

    fun save(note: Note) {
        pendingNote = note
    }

    override fun onCleared() {
        pendingNote?.let {
            NotesRepository.saveNote(it)
        }
    }
}