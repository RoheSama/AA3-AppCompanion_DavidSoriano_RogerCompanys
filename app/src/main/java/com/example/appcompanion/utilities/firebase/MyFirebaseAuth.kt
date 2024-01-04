package com.example.appcompanion.utilities.firebase

import android.app.Application
import com.example.appcompanion.R
import com.example.appcompanion.utilities.models.DbUser
import com.google.firebase.auth.FirebaseAuth

class MyFirebaseAuth(val appContext: Application) {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private var currentUser: DbUser? = null

    fun getUser() = currentUser
    fun setCurrentUser(newUser: DbUser) {
        currentUser = newUser
    }

    fun getAuthDbUser() : DbUser? {

        firebaseAuth.currentUser?.let { user ->
            val dbUser = DbUser(
                id = user.uid,
                email = user.email,
                username = user.displayName,
                photoPath = user.photoUrl.toString()
            )
            return dbUser
        }

        return null
    }
}