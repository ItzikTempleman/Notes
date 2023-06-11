package com.itzik.notes.project.models.note

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.itzik.notes.project.models.user.Gender
import com.itzik.notes.project.models.user.User
import com.itzik.notes.project.utils.Constants.NOTE_TABLE
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
@Entity(tableName = NOTE_TABLE)
data class Note(
    val noteContent: String,
    @PrimaryKey
    val id: String?=null,

    val user: @RawValue User=User("","","","","","","",Gender.MALE),
    val timeStamp: String,
    val isMarked: Boolean,
    val markedColor: MarkedColor?=null
): Parcelable

enum class MarkedColor {
    RED, YELLOW, BLUE, GREEN, ORANGE, PURPLE, GREY
}
