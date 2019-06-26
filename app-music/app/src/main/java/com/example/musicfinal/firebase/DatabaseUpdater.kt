package com.example.musicfinal.firebase

import com.example.musicfinal.model.User

interface DatabaseUpdater {
    fun onComplete(user: User)
}
