package com.itzik.notes.project.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

import com.itzik.notes.project.utils.Constants.NOTE_TABLE
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = NOTE_TABLE)
data class Note(
    val noteContent: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val timeStamp: String,
    val fontSize: Int,
    var isInTrashBin: Boolean
) : Parcelable



