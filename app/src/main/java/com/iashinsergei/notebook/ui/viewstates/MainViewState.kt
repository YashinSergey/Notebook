package com.iashinsergei.notebook.ui.viewstates

import com.iashinsergei.notebook.data.entity.Note

class MainViewState(val notes: List<Note>? = null, error: Throwable? = null) :
    BaseViewState<List<Note>?>(notes, error)