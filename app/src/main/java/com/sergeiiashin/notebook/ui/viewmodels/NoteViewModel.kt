package com.sergeiiashin.notebook.ui.viewmodels

import com.sergeiiashin.notebook.data.NoteResult
import com.sergeiiashin.notebook.data.NotesRepository
import com.sergeiiashin.notebook.data.entity.Note
import com.sergeiiashin.notebook.ui.viewstates.NoteViewState

class NoteViewModel(private val notesRepository: NotesRepository) : BaseViewModel<NoteViewState.Data, NoteViewState>() {

    private val pendingNote: Note?
        get() = viewStateLiveData.value?.data?.note

    fun save(note: Note) {
        viewStateLiveData.value = NoteViewState(NoteViewState.Data(note = note))
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
                is NoteResult.Success<*> -> viewStateLiveData.value = NoteViewState(
                    NoteViewState.Data(note = it.data as? Note))
                is NoteResult.Error -> viewStateLiveData.value = NoteViewState(error = it.error)
            }
        }
    }

    fun deleteNote() {
        pendingNote?.let {
            notesRepository.deleteNote(it.id).observeForever { res ->
                res?.let { result ->
                    when(result) {
                        is NoteResult.Success<*> -> viewStateLiveData.value = NoteViewState(NoteViewState.Data(isDeleted = true))
                        is NoteResult.Error -> viewStateLiveData.value = NoteViewState(error = result.error)
                    }
                }
            }
        }
    }
}