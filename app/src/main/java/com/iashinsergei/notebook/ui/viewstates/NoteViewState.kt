package com.iashinsergei.notebook.ui.viewstates

import com.iashinsergei.notebook.data.entity.Note

class NoteViewState(note: Note? = null, error: Throwable? = null) : BaseViewState<Note?>(note, error)