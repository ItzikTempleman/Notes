package com.itzik.notes_.project.data


import androidx.room.Database
import androidx.room.RoomDatabase

import com.itzik.notes_.project.model.Note
import com.itzik.notes_.project.model.User



@Database(entities = [Note::class, User::class], version = 4, exportSchema = false )
abstract class AppDatabase: RoomDatabase(){
    abstract fun getUserDao(): UserDao
    abstract fun getNoteDao(): NoteDao
}
