package com.iashinsergei.notebook.data

import com.iashinsergei.notebook.data.entity.Note

object NotesRepository {

    private val notes = listOf(
        Note(
            "First note",
            "This is first note field. Here you can record your thoughts and ideas."
        ),
        Note(
            "Second note",
            "This is second note field. Here you can record your thoughts and ideas."
        ),
        Note(
            "Third note",
            "This is third note field. Here you can record your thoughts and ideas."
        ),
        Note(
            "Fourth note",
            "This is fourth note field. Here you can record your thoughts and ideas."
        ),
        Note(
            "Fifth note",
            "This is fifth note field. Here you can record your thoughts and ideas."
        ),
        Note(
            "Sixth note",
            "This is sixth note field. Here you can record your thoughts and ideas."
        )
    )

    fun getNotes() = notes
}