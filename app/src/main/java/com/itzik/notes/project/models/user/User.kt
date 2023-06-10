package com.itzik.notes.project.models.user

data class User(
    val name: String,
    val familyName: String,
    val birthDate: String,
    val phoneNumber: String,
    val email: String,
    val password: String,
    val id: String,
    val gender: Gender
)

enum class Gender {
    MALE, FEMALE, OTHER
}
