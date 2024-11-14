package com.itzik.notes_.project.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itzik.notes_.project.model.Gender
import com.itzik.notes_.project.model.User
import com.itzik.notes_.project.model.WallpaperResponse
import com.itzik.notes_.project.repositories.AppRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repo: AppRepositoryInterface,
) : ViewModel() {

    private val privateLoggedInUsersList = MutableStateFlow<List<User>>(emptyList())
    val publicLoggedInUsersList: StateFlow<List<User>> = privateLoggedInUsersList

    private val privateUser = MutableStateFlow<User?>(null)
    val publicUser: StateFlow<User?> = privateUser

    init {
        viewModelScope.launch {
            fetchLoggedInUsers()
        }
    }

    fun fetchUserById(userId: String) {
        viewModelScope.launch {
            val user = repo.getUserById(userId)
            privateUser.value = user
        }
    }

    private fun fetchLoggedInUsers() {
        viewModelScope.launch {
            val users = repo.fetchLoggedInUsers()
            privateLoggedInUsersList.value = users
        }
    }

    fun registerUser(newUser: User) {
        viewModelScope.launch {
            try {
                repo.insertUser(newUser)
                fetchLoggedInUsers()
            } catch (_: Exception) {

            }
        }
    }

    fun getUserFromUserNameAndPassword(userName: String, password: String): Flow<User?> {
        val user = flow {
            val updatedUser = repo.getUserFromEmailAndPassword(userName, password)
            emit(updatedUser)
        }
        return user
    }

    fun getTempUserForVerification(userName: String): Flow<User> {
        val user = flow {
            val updatedUser = repo.getTempUserForVerification(userName)
            emit(updatedUser)
        }
        return user
    }

    suspend fun getAdminUserIfExists(email: String): User? {
        return try {
            repo.getTempUserForVerification(email)
        } catch (e: Exception) {
            null
        }
    }


    fun fetchViewType(userId: String) {
        viewModelScope.launch {
            val isViewGrid = repo.fetchViewType(userId)
            privateUser.value = privateUser.value?.copy(isViewGrid = isViewGrid)
        }
    }

    fun updateViewType(isViewGrid: Boolean) {
        viewModelScope.launch {
            privateUser.value?.let {
                repo.updateViewType(it.userId, isViewGrid)
                privateUser.value = it.copy(isViewGrid = isViewGrid)
            }
        }
    }


    suspend fun updateIsLoggedIn(user: User) {
        try {
            repo.updateIsLoggedIn(user)
            fetchLoggedInUsers()
        } catch (e: Exception) {
            Log.e("UserViewModel", "Error updating user login status: ${e.message}")
        }
    }

    fun createUser(
        name: String,
        email: String,
        password: String,
        phoneNumber: String,
        profileImage: String,
        gender: Gender,
        dateOfBirth: String,
        selectedWallpaper: String,
    ): User {
        return User(
            userName = name,
            email = email,
            password = password,
            isLoggedIn = true,
            phoneNumber = phoneNumber,
            profileImage = profileImage,
            gender = gender,
            dateOfBirth = dateOfBirth,
            selectedWallpaper = selectedWallpaper
        )
    }


    fun validateName(name: String) = name.length > 4

    fun validateEmail(email: String): Boolean {
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$")
        return email.matches(emailRegex)
    }

    fun validatePassword(password: String): Boolean {
        val passwordRegex =
            Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$")
        return password.matches(passwordRegex)
    }

    fun validatePhoneNumber(phoneNumber: String): Boolean {
        val regex = Regex("^(\\+\\d{1,3})?(0\\d{9}|\\d{9,11})$")
        return regex.matches(phoneNumber)
    }


    fun getAgeFromSDateString(date: String): String {
        val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val birthDate = LocalDate.parse(date, dateFormatter)
        val currentDate = LocalDate.now()
        val age = Period.between(birthDate, currentDate).years
        return age.toString()
    }


    fun getWallpaperList(searchQuery: String): Flow<WallpaperResponse> {
        val imagesFlow = flow {
            val response = repo.getWallpaperListByQuery(searchQuery)

            if (response.isSuccessful) {
                val responseBody = response.body()
                Log.d("TAGS", "responseBody: $responseBody")
                if (responseBody != null) {
                    emit(responseBody)
                } else Log.d("TAG", "first failure message: " + response.message())
                return@flow
            } else Log.d("TAG", "second failure message: " + response.message())
            return@flow
        }
        return imagesFlow

    }


    fun  updateUserField(
        profileImage:String?=null,
        newEmail: String? = null,
        newPhoneNumber: String? = null,
        newPassword: String? = null,
        selectedWallpaper: String? = null,
    ) {
        viewModelScope.launch {
            privateUser.value?.let { user ->
                val updatedUser = user.copy(
                    userId = user.userId,
                    profileImage= (profileImage?:user.profileImage),
                    email = (newEmail ?: user.email),
                    phoneNumber = (newPhoneNumber ?: user.phoneNumber),
                    password = (newPassword ?: user.password),
                    selectedWallpaper = (selectedWallpaper ?: user.selectedWallpaper),
                )
                repo.updateUserFields(
                    userId = user.userId,
                    profileImage= profileImage.toString(),
                    email = updatedUser.email,
                    phoneNumber = updatedUser.phoneNumber,
                    password = updatedUser.password,
                    selectedWallpaper = updatedUser.selectedWallpaper
                )
                privateUser.value = updatedUser
            }
        }
    }
}

