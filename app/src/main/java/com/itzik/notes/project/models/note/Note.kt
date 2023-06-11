package com.itzik.notes.project.models.note

import com.itzik.notes.project.models.user.User

data class Note(
    val noteContent: String,
    val noteId: String?=null,
    val user: User?=null,
    val timeStamp: String,
    val isMarked: Boolean,
    val markedColor: MarkedColor?=null
)

enum class MarkedColor {
    RED, YELLOW, BLUE, GREEN, ORANGE, PURPLE, GREY
}
