package com.iashinsergei.notebook.data

import com.iashinsergei.notebook.data.provider.FireStoreProvider
import com.iashinsergei.notebook.data.provider.RemoteDataProvider

object NotesRepository  {

    private val remoteDataProvider: RemoteDataProvider = FireStoreProvider()

    fun getNotes() = remoteDataProvider.subscribeToAllNotes()
    fun saveNote() = remoteDataProvider.saveNote()
    fun getNoteById() = remoteDataProvider.getNoteById()
}