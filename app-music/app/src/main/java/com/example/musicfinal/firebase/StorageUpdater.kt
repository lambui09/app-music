package com.example.musicfinal.firebase

interface StorageUpdater {
    fun onFail()
    fun onSuccess(path: String)
}
