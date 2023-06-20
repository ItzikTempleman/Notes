package com.itzik.notes.project.room_database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.itzik.notes.project.models.Note
import com.itzik.notes.project.utils.Converters

@Database(entities = [Note::class], version = 1)

@TypeConverters(Converters::class)


abstract class NoteDatabase : RoomDatabase() {

    abstract fun getDao(): NoteDao
}