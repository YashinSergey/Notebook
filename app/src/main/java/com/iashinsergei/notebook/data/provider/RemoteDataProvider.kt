package com.iashinsergei.notebook.data.provider

import androidx.lifecycle.LiveData
import com.iashinsergei.notebook.data.NoteResult
import com.iashinsergei.notebook.data.entity.Note

interface RemoteDataProvider {
    fun subscribeToAllNotes(): LiveData<NoteResult>
    fun getNoteById(id: String): LiveData<NoteResult>
    fun saveNote(note: Note): LiveData<NoteResult>

}