package com.example.musicfinal.database.updater

import com.example.musicfinal.database.model.Song

interface SongUpdater {
    fun getSongResult(result: List<Song>)
}
