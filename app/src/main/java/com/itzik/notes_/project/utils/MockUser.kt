package com.itzik.notes_.project.utils

import com.itzik.notes_.project.model.Gender
import com.itzik.notes_.project.model.User

fun getMockUser()=
    User(
        userName = "Guest",
        email = "guest@itzik_notes.com",
        password = "Yy4$",
        phoneNumber = "0545408531",
        gender = Gender.MALE,
        dateOfBirth = "26/09/1991",
        isViewGrid = false,
        profileImage = "",
        selectedWallpaper=""

    )