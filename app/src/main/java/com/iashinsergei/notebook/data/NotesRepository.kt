package com.iashinsergei.notebook.data

import android.graphics.Color
import com.iashinsergei.notebook.data.entity.Note

object NotesRepository {
    private val notes = listOf(
        Note(
            "First note",
            "This is first note",
            Color.BLUE
        ),
        Note(
            "Second note",
            "This is second note",
            Color.RED
        ),
        Note(
            "Third note",
            "This is third note",
            Color.CYAN
        ),
        Note(
            "Fourth note",
            "This is fourth note",
            Color.GRAY
        ),
        Note(
            "Fifth note",
            "This is fifth note",
            Color.GREEN
        ),
        Note(
            "Sixth note",
            "This is sixth note",
            Color.MAGENTA
        )
    )

}