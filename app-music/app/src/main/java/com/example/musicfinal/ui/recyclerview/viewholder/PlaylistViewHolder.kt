package com.example.musicfinal.ui.recyclerview.viewholder

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.musicfinal.R
import com.example.musicfinal.ui.recyclerview.listener.PlaylistViewHolderListener

class PlaylistViewHolder(itemView: View?,val listener: PlaylistViewHolderListener) : RecyclerView.ViewHolder(itemView) {
    var index = 0
    val tvNamePlaylist: TextView? = itemView?.findViewById(R.id.tvNamePlaylist)
    val tvCountSongOfPlaylist: TextView? = itemView?.findViewById(R.id.tvCountSong)

    init {
        val cardViewMain:CardView? = itemView?.findViewById(R.id.cardViewMain)
        cardViewMain?.setOnClickListener {
            listener.addObjectOnClick(index)
        }
    }
}
