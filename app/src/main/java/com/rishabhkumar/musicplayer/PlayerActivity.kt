package com.rishabhkumar.musicplayer

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rishabhkumar.musicplayer.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {
    companion object {
        lateinit var musicListPlayer : ArrayList<Music>
        var songPosition: Int = 0
        var mediaPlayer: MediaPlayer ?= null

    }

    private lateinit var binding : ActivityPlayerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.customPink)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        songPosition = intent.getIntExtra("index",0)
        when(intent.getStringExtra("class")){
            "MusicAdapter"->{
                musicListPlayer = ArrayList()
                musicListPlayer.addAll(MainActivity.musicListMA)
                if(mediaPlayer == null){
                    mediaPlayer = MediaPlayer()
                }
                mediaPlayer!!.reset()
                mediaPlayer!!.setDataSource(musicListPlayer[songPosition].path)
                mediaPlayer!!.prepare()
                mediaPlayer!!.start()
            }
        }
    }
}