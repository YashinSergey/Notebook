package com.sergeiiashin.notebook.data

import com.sergeiiashin.notebook.data.entity.Note
import com.sergeiiashin.notebook.data.provider.FireStoreProvider
import com.sergeiiashin.notebook.data.provider.RemoteDataProvider

object NotesRepository  {

    private val remoteDataProvider: RemoteDataProvider = FireStoreProvider()

    fun getNotes() = remoteDataProvider.subscribeToAllNotes()
    fun getNoteById(id: String) = remoteDataProvider.getNoteById(id)
    fun saveNote(note: Note) = remoteDataProvider.saveNote(note)
    fun getCurrentUser() = remoteDataProvider.getCurrentUser()
}