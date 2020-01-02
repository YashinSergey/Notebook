package com.sergeiiashin.notebook.ui.viewstates

import com.sergeiiashin.notebook.data.entity.Note

class NoteViewState(note: Note? = null, error: Throwable? = null) : BaseViewState<Note?>(note, error)