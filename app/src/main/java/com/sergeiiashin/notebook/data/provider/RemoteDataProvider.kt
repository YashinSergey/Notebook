package com.sergeiiashin.notebook.data.provider

import com.sergeiiashin.notebook.data.NoteResult
import com.sergeiiashin.notebook.data.entity.Note
import com.sergeiiashin.notebook.data.entity.User
import kotlinx.coroutines.channels.ReceiveChannel

interface RemoteDataProvider {
    fun subscribeToAllNotes(): ReceiveChannel<NoteResult>
    suspend fun getNoteById(id: String): Note
    suspend fun saveNote(note: Note): Note
    suspend fun getCurrentUser(): User?
    suspend fun deleteNote(noteId: String): Unit
}