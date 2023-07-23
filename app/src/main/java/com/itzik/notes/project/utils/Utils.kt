package com.itzik.notes.project.utils

import com.itzik.notes.project.models.Note
import com.itzik.notes.project.viewmodels.NoteViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Constants {
    const val NOTE_TABLE = "noteTable"
    const val NOTE_DATABASE = "noteDatabase"
}

suspend fun saveNote(newChar: String, fontSize: String, noteViewModel: NoteViewModel) {
    val time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
    val note = Note(
        noteContent = newChar,
        timeStamp = time,
        fontSize = fontSize.toInt(),
        isInTrashBin = false
    )
    noteViewModel.saveNote(note)
}
