package com.iashinsergei.notebook.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Note(val id: String, val title: String, val text: String, val color: Int?) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if(this === other) return true
        if(javaClass != other?.javaClass) return false

        other as Note
        if(id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + text.hashCode()
        result = 31 * result + (color ?: 0)
        return result
    }

    enum class Color {
        DEFAULT_THEME_COLOR,
        ORANGE_THEME_COLOR,
        BLUE_THEME_COLOR,
        BROWN_THEME_COLOR,
        YELLOW_THEME_COLOR,
        IRIS_BLUE_THEME_COLOR,
        DARK_THEME_COLOR
    }
}