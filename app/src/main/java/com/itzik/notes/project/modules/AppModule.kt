package com.itzik.notes.project.modules

import android.content.Context
import androidx.room.Room
import com.itzik.notes.project.repositories.NoteRepository
import com.itzik.notes.project.repositories.NoteRepositoryImp
import com.itzik.notes.project.room_database.NoteDao
import com.itzik.notes.project.room_database.NoteDatabase
import com.itzik.notes.project.utils.Constants.NOTE_DATABASE
import com.itzik.notes.project.utils.Converters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val typeConverter= Converters()

    @Provides
    @Singleton
    fun provideNoteRepository(noteDao: NoteDao): NoteRepository = NoteRepositoryImp(noteDao)

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context) = Room.databaseBuilder(
    context, NoteDatabase::class.java, NOTE_DATABASE)
    .allowMainThreadQueries()
    .fallbackToDestructiveMigration()
    .build()


    @Provides
    @Singleton
    fun provideDao(appDatabase: NoteDatabase): NoteDao = appDatabase.getDao()

}