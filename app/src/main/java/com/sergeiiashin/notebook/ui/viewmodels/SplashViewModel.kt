package com.sergeiiashin.notebook.ui.viewmodels

import com.sergeiiashin.notebook.data.NotesRepository
import com.sergeiiashin.notebook.data.exceptions.NoAuthException
import com.sergeiiashin.notebook.ui.viewstates.SplashViewState

class SplashViewModel(private val notesRepository: NotesRepository) : BaseViewModel<Boolean?, SplashViewState>() {

    fun requestUser() {
        notesRepository.getCurrentUser().observeForever{
            viewStateLiveData.value = if (it != null) {
                SplashViewState(authenticated = true)
            } else {
                SplashViewState(error = NoAuthException())
            }
        }
    }
}