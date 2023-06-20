package com.itzik.notes.project.utils

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.itzik.notes.project.models.Note

@ProvidedTypeConverter
class Converters {

    @TypeConverter
    fun fromNote(note: Note):String =Gson().toJson(note)

    @TypeConverter
    fun toNote(note: String): Note =Gson().fromJson(note, object : TypeToken<Note>() {}.type)

}
