package com.example.musicfinal.service

import android.os.Binder
import java.lang.ref.WeakReference

internal class MusicBinder(musicPlayer: MusicPlayer) : Binder() {
    private val musicPlayerReference: WeakReference<MusicPlayer> = WeakReference(musicPlayer)
    fun getMusicPlayer(): MusicPlayer? = musicPlayerReference.get()
}
