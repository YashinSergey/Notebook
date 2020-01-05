package com.sergeiiashin.notebook.ui.viewmodels

import com.sergeiiashin.notebook.data.NotesRepository
import com.sergeiiashin.notebook.data.exceptions.NoAuthException
import com.sergeiiashin.notebook.ui.viewstates.SplashViewState

class SplashViewModel : BaseViewModel<Boolean?, SplashViewState>() {

    fun requestUser() {
        NotesRepository.getCurrentUser().observeForever{
            viewStateLiveData.value = if (it != null) {
                SplashViewState(authenticated = true)
            } else {
                SplashViewState(error = NoAuthException())
            }
        }
    }
}