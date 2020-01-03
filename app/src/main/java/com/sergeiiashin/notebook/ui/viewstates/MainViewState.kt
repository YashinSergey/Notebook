package com.sergeiiashin.notebook.ui.viewstates

import com.sergeiiashin.notebook.data.entity.Note

class MainViewState(val notes: List<Note>? = null, error: Throwable? = null) :
    BaseViewState<List<Note>?>(notes, error)