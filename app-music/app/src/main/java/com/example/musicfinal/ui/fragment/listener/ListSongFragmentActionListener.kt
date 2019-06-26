package com.example.musicfinal.ui.fragment.listener

interface ListSongFragmentActionListener {
    fun onStartPlay(songId: Long)
    fun onFavoriteChange(songId: Long)
}
