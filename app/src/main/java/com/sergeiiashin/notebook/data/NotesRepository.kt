package com.sergeiiashin.notebook.data

import com.sergeiiashin.notebook.data.entity.Note
import com.sergeiiashin.notebook.data.provider.RemoteDataProvider

class NotesRepository(private val remoteDataProvider: RemoteDataProvider)  {

    fun getNotes() = remoteDataProvider.subscribeToAllNotes()
    suspend fun getNoteById(id: String) = remoteDataProvider.getNoteById(id)
    suspend fun saveNote(note: Note) = remoteDataProvider.saveNote(note)
    suspend fun getCurrentUser() = remoteDataProvider.getCurrentUser()
    suspend fun deleteNote(noteId: String) = remoteDataProvider.deleteNote(noteId)
}