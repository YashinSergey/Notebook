package com.iashinsergei.notebook.data

import androidx.lifecycle.MutableLiveData
import com.iashinsergei.notebook.data.entity.Note
import java.util.*

object NotesRepository {

    private val notesLiveData = MutableLiveData<List<Note>>()

    private val notes = mutableListOf(
        Note(
            UUID.randomUUID().toString(),
            "First note",
            "This is first note field. Here you can record your thoughts and ideas."
        ),
        Note(
            UUID.randomUUID().toString(),
            "Second note",
            "This is second note field. Here you can record your thoughts and ideas."
        ),
        Note(
            UUID.randomUUID().toString(),
            "Third note",
            "This is third note field. Here you can record your thoughts and ideas."
        ),
        Note(
            UUID.randomUUID().toString(),
            "Fourth note",
            "This is fourth note field. Here you can record your thoughts and ideas."
        ),
        Note(
            UUID.randomUUID().toString(),
            "Fifth note",
            "This is fifth note field. Here you can record your thoughts and ideas."
        ),
        Note(
            UUID.randomUUID().toString(),
            "Sixth note",
            "This is sixth note field. Here you can record your thoughts and ideas."
        )
    )

    init {
        notesLiveData.value = notes
    }

    fun getNotes() = notesLiveData

    fun saveNote(note: Note) {
        addOrReplace(note)
        notesLiveData.value = notes
    }

    private fun addOrReplace(note: Note) {
        for(i in notes.indices) {
            if(notes[i] == note) {
                notes[i] = note
                return
            }
        }
        notes.add(note)
    }
}