package com.sergeiiashin.notebook.ui.viewmodels

import com.sergeiiashin.notebook.data.NoteResult
import com.sergeiiashin.notebook.data.NotesRepository
import com.sergeiiashin.notebook.data.entity.Note
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainViewModel(notesRepository: NotesRepository) : BaseViewModel<List<Note>?>() {

    private val notesChannel = notesRepository.getNotes()

    init {
        launch {
            notesChannel.consumeEach {
                @Suppress("UNCHECKED_CAST")
                when(it) {
                    is NoteResult.Success<*> -> setData(it.data as? List<Note>)
                    is NoteResult.Error -> setError(it.error)
                }
            }
        }
    }

    override fun onCleared() {
        notesChannel.cancel()
        super.onCleared()
    }
}