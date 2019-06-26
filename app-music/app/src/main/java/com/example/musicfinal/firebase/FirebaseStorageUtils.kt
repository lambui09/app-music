package com.example.musicfinal.firebase

import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.example.musicfinal.utils.bitmapToByteArray
import java.util.*

object FirebaseStorageUtils {
    private val TAG = FirebaseStorageUtils::class.qualifiedName
    private const val URL_IMAGE = "gs://asiantech-intern-final.appspot.com"
    private val storage = FirebaseStorage.getInstance()

    fun uploadImage(uploadImage: Bitmap, storageUpdater: StorageUpdater) {
        val storageRef = storage.getReferenceFromUrl(URL_IMAGE.trim())
        val pathString = "${Calendar.getInstance().timeInMillis}.png"
        val mountainsRef = storageRef.child(pathString)
        val data = bitmapToByteArray(uploadImage)
        val uploadTask = mountainsRef.putBytes(data)

        uploadTask.addOnFailureListener {
            storageUpdater.onFail()
        }.addOnSuccessListener {
            storageUpdater.onSuccess(pathString.trim())
        }
    }

    fun getDownloadUrl(pathString: String, updater: GetDownloadUpdater) {
        val storageRef = storage.getReferenceFromUrl(URL_IMAGE.trim())
        val mountainsRef = storageRef.child(pathString)
        mountainsRef.downloadUrl.addOnSuccessListener {
            Log.e(TAG, it.toString())
            updater.handle(it.path)
        }.addOnFailureListener {
            Log.e(TAG, it.toString())
        }
    }
}
