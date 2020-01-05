package com.sergeiiashin.notebook.data.provider

import androidx.lifecycle.LiveData
import com.sergeiiashin.notebook.data.NoteResult
import com.sergeiiashin.notebook.data.entity.Note
import com.sergeiiashin.notebook.data.entity.User

interface RemoteDataProvider {
    fun subscribeToAllNotes(): LiveData<NoteResult>
    fun getNoteById(id: String): LiveData<NoteResult>
    fun saveNote(note: Note): LiveData<NoteResult>
    fun getCurrentUser(): LiveData<User?>

}