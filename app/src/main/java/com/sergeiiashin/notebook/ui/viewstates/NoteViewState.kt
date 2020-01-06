package com.sergeiiashin.notebook.ui.viewstates

import com.sergeiiashin.notebook.data.entity.Note

class NoteViewState(data: Data = Data(), error: Throwable? = null) : BaseViewState<NoteViewState.Data>(data, error) {
    data class Data(val isDeleted: Boolean = false, val note: Note? = null)
}