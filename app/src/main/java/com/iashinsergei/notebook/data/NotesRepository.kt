package com.iashinsergei.notebook.data

import com.iashinsergei.notebook.data.entity.Note
import java.util.*

object NotesRepository {

    private val notes = listOf(
        Note(
            UUID.randomUUID().toString(),
            "First note",
            "This is first note field. Here you can record your thoughts and ideas.",
            null
        ),
        Note(
            UUID.randomUUID().toString(),
            "Second note",
            "This is second note field. Here you can record your thoughts and ideas.",
            null
        ),
        Note(
            UUID.randomUUID().toString(),
            "Third note",
            "This is third note field. Here you can record your thoughts and ideas.",
            null
        ),
        Note(
            UUID.randomUUID().toString(),
            "Fourth note",
            "This is fourth note field. Here you can record your thoughts and ideas.",
            null
        ),
        Note(
            UUID.randomUUID().toString(),
            "Fifth note",
            "This is fifth note field. Here you can record your thoughts and ideas.",
            null
        ),
        Note(
            UUID.randomUUID().toString(),
            "Sixth note",
            "This is sixth note field. Here you can record your thoughts and ideas.",
            null
        )
    )

    fun getNotes() = notes
}