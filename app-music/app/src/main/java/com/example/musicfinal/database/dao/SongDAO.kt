package com.example.musicfinal.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.musicfinal.database.model.Song

@Dao
interface SongDAO {

    @Query("Select * from song")
    fun getAllSong(): List<Song>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addSong(song: Song)

    @Query("Delete from song where id= :id")
    fun deleteSongWithId(id: Long)
}
