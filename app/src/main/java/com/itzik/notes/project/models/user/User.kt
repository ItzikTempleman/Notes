package com.itzik.notes.project.models.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name: String,
    val familyName: String,
    val birthDate: String,
    val phoneNumber: String,
    val email: String,
    val password: String,
    val id: String,
    val gender: Gender
):Parcelable

enum class Gender {
    MALE, FEMALE, OTHER
}
