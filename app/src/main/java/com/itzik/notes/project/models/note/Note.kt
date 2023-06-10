package com.itzik.notes.project.models.note

import com.itzik.notes.project.models.user.User

data class Note(
    val noteTitle: String,
    val noteContent: String,
    val noteId: String,
    val user: User,
    val timeStamp: String,
    val isMarked: Boolean,
    val markedColor: MarkedColor
)

enum class MarkedColor {
    RED, YELLOW, BLUE, GREEN, ORANGE, PURPLE, GREY
}
