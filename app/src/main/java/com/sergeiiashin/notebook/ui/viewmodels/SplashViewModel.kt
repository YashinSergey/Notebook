package com.sergeiiashin.notebook.ui.viewmodels

import com.sergeiiashin.notebook.data.NotesRepository
import com.sergeiiashin.notebook.data.exceptions.NoAuthException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class SplashViewModel(private val notesRepository: NotesRepository) : BaseViewModel<Boolean?>() {

    fun requestUser() {
        launch {
            notesRepository.getCurrentUser()?.let {
                setData(true)
            } ?: setError(NoAuthException())
        }
    }
}