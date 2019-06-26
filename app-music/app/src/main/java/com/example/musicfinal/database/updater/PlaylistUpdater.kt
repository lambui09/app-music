package com.example.musicfinal.database.updater

import com.example.musicfinal.database.model.Playlist

interface PlaylistUpdater {
    fun getPlaylistResult(result: List<Playlist>)
}
