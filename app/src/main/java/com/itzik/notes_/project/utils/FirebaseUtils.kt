package com.itzik.notes_.project.utils

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.itzik.notes_.project.main.NoteApp
import java.util.concurrent.TimeUnit

fun makeToast(str: String, e: FirebaseException? = null) {
    Log.e("TAG", str, e)
    Toast.makeText(NoteApp.instance, str, Toast.LENGTH_SHORT).show()
}


fun FirebaseAuth.sendVerificationCode(
    phoneNumber: String,
    activity: Activity,
    onCodeSent: (String, PhoneAuthProvider.ForceResendingToken) -> Unit,
    onVerificationCompleted: (PhoneAuthCredential) -> Unit,
    onVerificationFailed: (FirebaseException) -> Unit,
) {
    val options = PhoneAuthOptions.newBuilder(this)
        .setPhoneNumber(phoneNumber)
        .setTimeout(60L, TimeUnit.SECONDS)
        .setActivity(activity)
        .setCallbacks(
            object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    onVerificationCompleted(credential)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    onVerificationFailed(e)
                }

                override fun onCodeSent(verificationCode: String, token: PhoneAuthProvider.ForceResendingToken) {
                    onCodeSent(verificationCode, token)
                }
            }
        )
        .build()
    PhoneAuthProvider.verifyPhoneNumber(options)
}