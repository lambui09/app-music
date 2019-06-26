package com.example.musicfinal.database

import android.content.Context
import com.example.musicfinal.R
import com.example.musicfinal.database.model.Playlist
import com.example.musicfinal.database.model.Song
import com.example.musicfinal.utils.querySongs

class DataController(context: Context) {
    val songs = mutableListOf<Song>()
    private val appDataHelper = AppDataHelper.getInstance(context)
    private val historyPlaylistName=context.getString(R.string.history)
    private val favoritePlaylistName= context.getString(R.string.favourite)

    init {
        songs.addAll(querySongs(context.contentResolver))
        for (song in songs) {
            appDataHelper.addSong(song)
        }
        appDataHelper.addPlaylist(Playlist(historyPlaylistName))
        appDataHelper.addPlaylist(Playlist(favoritePlaylistName))
    }

    fun changeFavoriteState(songId: Long) {
        for (song in songs) if (song.id == songId) {
            song.changeFavouriteState()
            if(song.isFavourite){
                appDataHelper.addSongToPlaylist(favoritePlaylistName,songId)
            }else{
                appDataHelper.deleteSongInPlaylist(favoritePlaylistName,songId)
            }
            break
        }
    }
}
