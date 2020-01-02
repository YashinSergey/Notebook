package com.iashinsergei.notebook.data

import com.iashinsergei.notebook.data.entity.Note
import com.iashinsergei.notebook.data.provider.FireStoreProvider
import com.iashinsergei.notebook.data.provider.RemoteDataProvider

object NotesRepository  {

    private val remoteDataProvider: RemoteDataProvider = FireStoreProvider()

    fun getNotes() = remoteDataProvider.subscribeToAllNotes()
    fun getNoteById(id: String) = remoteDataProvider.getNoteById(id)
    fun saveNote(note: Note) = remoteDataProvider.saveNote(note)
}