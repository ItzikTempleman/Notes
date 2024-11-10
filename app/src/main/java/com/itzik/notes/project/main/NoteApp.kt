package com.itzik.notes.project.main

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NoteApp : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        instance = this
    }

    companion object {
        lateinit var instance: NoteApp
        private set
    }


}