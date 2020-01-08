package com.sergeiiashin.notebook.ui.viewmodels

import com.sergeiiashin.notebook.data.NotesRepository
import com.sergeiiashin.notebook.data.entity.Note
import com.sergeiiashin.notebook.ui.viewstates.NoteData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class NoteViewModel(private val notesRepository: NotesRepository) : BaseViewModel<NoteData>() {

    private val currentNote: Note?
        get() = getViewState().poll()?.note

    fun save(note: Note) {
        setData(NoteData(note = note))
    }

    override fun onCleared() {
        launch {
            currentNote?.let {
                notesRepository.saveNote(it)
            }
            super.onCleared()
        }
    }

    fun loadNote(id: String) {
        launch {
            try {
                setData(NoteData(note = notesRepository.getNoteById(id)))
            } catch (error: Throwable) {
                setError(error)
            }
        }
    }

    fun deleteNote() {
        launch {
            try {
                currentNote?.let { notesRepository.deleteNote(it.id) }
                setData(NoteData(isDeleted = true))
            } catch (error: Throwable) {
                setError(error)
            }
        }
    }
}