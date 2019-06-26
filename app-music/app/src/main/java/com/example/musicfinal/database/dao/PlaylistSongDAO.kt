package com.example.musicfinal.database.dao

import android.arch.persistence.room.*
import com.example.musicfinal.database.model.PlaylistSong
import com.example.musicfinal.database.model.Song

@Dao
interface PlaylistSongDAO {

    @Query(" Select * from song where id in (select song_id from playlist_song where playlist_name = :playlistName)")
    fun getSongOfPlaylist(playlistName: String): List<Song>

    @Delete()
    fun deleteSongInPlaylist(playlistSong: PlaylistSong)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSongToPlaylist(playlistSong: PlaylistSong)
}
