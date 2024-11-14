package com.itzik.notes_.project.utils

import com.itzik.notes_.project.model.Gender
import com.itzik.notes_.project.model.User

fun getMockUser()=
    User(
        userName = "Guest",
        email = "guest@itzik_notes.com",
        password = "Yy4$",
        phoneNumber = "0000000000",
        gender = Gender.OTHER,
        dateOfBirth = "01/01/2000",
        isViewGrid = false,
        profileImage = "",
        selectedWallpaper=""

    )