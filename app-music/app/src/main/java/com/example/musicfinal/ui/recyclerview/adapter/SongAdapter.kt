package com.example.musicfinal.ui.recyclerview.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.musicfinal.R
import com.example.musicfinal.database.model.Song
import com.example.musicfinal.ui.fragment.listener.ListSongFragmentActionListener
import com.example.musicfinal.ui.recyclerview.viewholder.SongViewHolder

class SongAdapter(private val musics: List<Song>, private val context: Context?,
                  private val listener: ListSongFragmentActionListener)
    : RecyclerView.Adapter<SongViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(context)
                .inflate(R.layout.list_item_music, parent, false)
        return SongViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return musics.size
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.tvTitleSong?.text = musics[position].title
        holder.tvSinger?.text = musics[position].artist
        holder.songId = musics[position].id
        if (musics[position].isFavourite) {
            holder.imgFavourite?.setImageResource(R.drawable.ic_like)
        } else {
            holder.imgFavourite?.setImageResource(R.drawable.ic_unlike)
        }
    }
}
