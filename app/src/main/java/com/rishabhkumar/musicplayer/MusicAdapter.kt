package com.rishabhkumar.musicplayer

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rishabhkumar.musicplayer.databinding.MusicViewBinding

class MusicAdapter(private val context : Context,  private val musicList:ArrayList<String>) : RecyclerView.Adapter<MusicAdapter.MyHolder> () {
    class MyHolder(binding: MusicViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.txtSongName
        val album = binding.txtSongAlbum
        val image = binding.imgMusicView
        val duration = binding.txtSongDuration
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicAdapter.MyHolder {
        return MyHolder(MusicViewBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    //called when music view has to displayed
    override fun onBindViewHolder(holder: MusicAdapter.MyHolder, position: Int) {
        holder.title.text = musicList[position]
    }

    override fun getItemCount(): Int {
        return musicList.size
    }
}