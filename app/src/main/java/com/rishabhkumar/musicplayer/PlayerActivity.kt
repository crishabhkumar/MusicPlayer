package com.rishabhkumar.musicplayer

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rishabhkumar.musicplayer.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {
    companion object {
        lateinit var musicListPlayer : ArrayList<Music>
        var songPosition: Int = 0
        var mediaPlayer: MediaPlayer ?= null
        var isPlaying : Boolean = false
    }

    private lateinit var binding : ActivityPlayerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.customPink)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeLayout()
        binding.btnPlayAndPause.setOnClickListener{
            if(isPlaying) pauseMusic()
            else playMusic()
        }
    }

    private fun setLayout(){
        Glide.with(this)
            .load(musicListPlayer[songPosition].artUri)
            .apply(RequestOptions().placeholder(R.drawable.splash_screen).centerCrop())
            .into(binding.imgSongImagePlayerActivity)
        binding.txtSongNamePlayerActivity.text = musicListPlayer[songPosition].title

    }

    private fun createMediaPlayer(){
        try{
            if(mediaPlayer == null){
                mediaPlayer = MediaPlayer()
            }
            mediaPlayer!!.reset()
            mediaPlayer!!.setDataSource(musicListPlayer[songPosition].path)
            mediaPlayer!!.prepare()
            mediaPlayer!!.start()
            isPlaying = true
            binding.btnPlayAndPause.setIconResource(R.drawable.pause_icon)
        }catch (e: Exception){
            return
        }
    }
    private fun initializeLayout(){
        songPosition = intent.getIntExtra("index",0)
        when(intent.getStringExtra("class")){
            "MusicAdapter"->{
                musicListPlayer = ArrayList()
                musicListPlayer.addAll(MainActivity.musicListMA)
                setLayout()
                createMediaPlayer()
            }
        }
    }

    private fun playMusic(){
        binding.btnPlayAndPause.setIconResource(R.drawable.pause_icon)
        isPlaying = true
        mediaPlayer!!.start()
    }
    private fun pauseMusic(){
        binding.btnPlayAndPause.setIconResource(R.drawable.play_icon)
        isPlaying = false
        mediaPlayer!!.pause()
    }
}