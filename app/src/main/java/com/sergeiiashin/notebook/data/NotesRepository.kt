package com.sergeiiashin.notebook.data

import com.sergeiiashin.notebook.data.entity.Note
import com.sergeiiashin.notebook.data.provider.RemoteDataProvider

class NotesRepository(private val remoteDataProvider: RemoteDataProvider)  {

    fun getNotes() = remoteDataProvider.subscribeToAllNotes()
    fun getNoteById(id: String) = remoteDataProvider.getNoteById(id)
    fun saveNote(note: Note) = remoteDataProvider.saveNote(note)
    fun getCurrentUser() = remoteDataProvider.getCurrentUser()
    fun deleteNote(noteId: String) = remoteDataProvider.deleteNote(noteId)
}