package com.iashinsergei.notebook.data

import com.iashinsergei.notebook.data.entity.Note

object NotesRepository {

    private val notes = listOf(
        Note(
            "First note",
            "This is first note field. Here you can record your thoughts and ideas.",
            0xfff06292.toInt()
        ),
        Note(
            "Second note",
            "This is second note field. Here you can record your thoughts and ideas.",
            0xff9575cd.toInt()
        ),
        Note(
            "Third note",
            "This is third note field. Here you can record your thoughts and ideas.",
            0xff64b5f6.toInt()
        ),
        Note(
            "Fourth note",
            "This is fourth note field. Here you can record your thoughts and ideas.",
            0xff4db6ac.toInt()
        ),
        Note(
            "Fifth note",
            "This is fifth note field. Here you can record your thoughts and ideas.",
            0xffb2ff59.toInt()
        ),
        Note(
            "Sixth note",
            "This is sixth note field. Here you can record your thoughts and ideas.",
            0xffffeb3b.toInt()
        )
    )

    fun getNotes() = notes
}