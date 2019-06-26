package com.example.musicfinal.firebase

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth

object FirebaseAuthUtils {
    private val auth = FirebaseAuth.getInstance()

    fun login(mail: String, password: String, activity: Activity, authUpdater: AuthUpdater) {
        auth.signInWithEmailAndPassword(mail, password)
                .addOnCompleteListener(activity) { task ->
                    if (task.isSuccessful) {
                        authUpdater.onSuccess()
                    } else {
                        authUpdater.onFail()
                    }
                }
    }

    fun createUser(mail: String, password: String, activity: Activity, authUpdater: AuthUpdater) {
        auth?.createUserWithEmailAndPassword(mail, password)
                ?.addOnCompleteListener(activity) { task ->
                    if (task.isSuccessful) {
                        authUpdater.onSuccess()
                    } else {
                        authUpdater.onFail()
                    }
                }
    }

    fun resetPassword(newPassword: String, activity: Activity, authUpdater: AuthUpdater) {
        val currentUser = auth.currentUser
        currentUser?.updatePassword(newPassword)
                ?.addOnCompleteListener(activity) { task ->
                    if (!task.isSuccessful) {
                        authUpdater.onSuccess()
                    } else {
                        authUpdater.onFail()
                    }
                }
    }
}
