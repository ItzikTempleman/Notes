package com.itzik.notes_.project.utils

import com.itzik.notes_.project.model.Note
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object Constants {
    const val NAX_PINNED_NOTES = 3
    const val USER_TABLE = "userTable"
    const val NOTE_TABLE = "noteTable"

    const val BASE_URL = "https://pixabay.com/"
    const val API_KEY_VALUE = "46102404-ad517a06d05ad3776aea392e3"

    const val MY_BACKEND_BASE_URL = "https://my-notes-app-itzik-0c1e8b06170b.herokuapp.com/"
}
fun reverseDateFormat(date: String): String {
//    val localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    val localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return localDate.format(formatter)
}

fun getFormattedTime(): String {
    val instant = Instant.parse(Note.getCurrentTime())
    val formatter = DateTimeFormatter.ofPattern("HH:mm").withZone(ZoneId.systemDefault())
    return formatter.format(instant)
}

