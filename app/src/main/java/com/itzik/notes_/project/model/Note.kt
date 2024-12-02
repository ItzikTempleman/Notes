package com.itzik.notes_.project.model

import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

import com.itzik.notes_.project.utils.Constants.NOTE_TABLE
import kotlinx.parcelize.Parcelize
import java.time.Instant



@Parcelize
@Entity(
    tableName = NOTE_TABLE,
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["userId"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["userId"])]
)

data class Note(
    @PrimaryKey(autoGenerate = true)
    var noteId: Int = 0,
    var content: String,
    var time: String = getCurrentTime(),
    var isInTrash: Boolean = false,
    var isStarred: Boolean = false,
    var isPinned: Boolean = false,
    var fontColor: Int = Color.Black.toArgb(),
    var fontSize: Int = 20,
    var userId: String,
    var fontWeight:Int=400
) : Parcelable {

    companion object {
        fun getCurrentTime(): String {
            return Instant.now().toString()
        }
    }
}




//@Parcelize
//@Entity(
//    tableName = NOTE_TABLE,
//    foreignKeys = [ForeignKey(
//        entity = User::class,
//        parentColumns = ["userId"],
//        childColumns = ["userId"],
//        onDelete = ForeignKey.CASCADE
//    )],
//    indices = [Index(value = ["userId"])]
//)
//data class Note(
//    @PrimaryKey(autoGenerate = false)
//    var noteId: Int = 0,
//    var content: String,
//    var time: String = getCurrentTime(),
//    var isInTrash: Boolean = false,
//    var isStarred: Boolean = false,
//    var isPinned: Boolean = false,
//    var fontColor: Int = Color.Black.toArgb(),
//    var fontSize: Int = 20,
//    var userId: String,
//    var fontWeight: Int = 400
//) : Parcelable {
//
//    companion object {
//        private val noteIdMap = mutableMapOf<String, Int>()
//
//        fun generateNoteId(userId: String): Int {
//            val numericPart = userId.filter { it.isDigit() }
//            val baseId = (numericPart + "1").take(8).toIntOrNull()
//                ?: throw IllegalStateException("Invalid userId format")
//            val lastId = noteIdMap[userId] ?: (baseId * 10)
//            val newId = lastId + 1
//            noteIdMap[userId] = newId
//            return newId
//        }
//
//        fun getCurrentTime(): String = Instant.now().toString()
//    }
//}
//
//
