package com.rishabhkumar.musicplayer

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rishabhkumar.musicplayer.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity(),ServiceConnection {
    companion object {
        lateinit var musicListPlayer : ArrayList<Music>
        var songPosition: Int = 0
        var isPlaying : Boolean = false //to check whether song is currently playing or not
        var musicService :MusicService ?= null
    }

    private lateinit var binding : ActivityPlayerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.customPink)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //for starting the service
        val intent = Intent(this,MusicService::class.java)
        bindService(intent,this, BIND_AUTO_CREATE)
        startService(intent)

        initializeLayout()
        binding.btnPlayAndPause.setOnClickListener{
            if(isPlaying) pauseMusic()
            else playMusic()
        }
        binding.btnPreviourPlayer.setOnClickListener{
            prevNextSong(false)
        }
        binding.btnNextPlayer.setOnClickListener{
            prevNextSong(true)
        }
    }

    //function for setting up the layout
    //means setting up the image of sng in media player
    private fun setLayout(){
        Glide.with(this)
            .load(musicListPlayer[songPosition].artUri)
            .apply(RequestOptions().placeholder(R.drawable.splash_screen).centerCrop())
            .into(binding.imgSongImagePlayerActivity)
        binding.txtSongNamePlayerActivity.text = musicListPlayer[songPosition].title

    }

    //function for creating the media player
    private fun createMediaPlayer(){
        try{
            if(musicService!!.mediaPlayer == null){
                musicService!!.mediaPlayer = MediaPlayer()
            }
            musicService!!.mediaPlayer!!.reset()
            musicService!!.mediaPlayer!!.setDataSource(musicListPlayer[songPosition].path)
            musicService!!.mediaPlayer!!.prepare()
            musicService!!.mediaPlayer!!.start()
            isPlaying = true
            binding.btnPlayAndPause.setIconResource(R.drawable.pause_icon)
        }catch (e: Exception){
            return
        }
    }

    //fucntion for initializing the layout
    private fun initializeLayout(){
        songPosition = intent.getIntExtra("index",0)
        when(intent.getStringExtra("class")){
            "MusicAdapter"->{
                musicListPlayer = ArrayList()
                musicListPlayer.addAll(MainActivity.musicListMA)
                setLayout()
//                createMediaPlayer()

            }
            "MainActivity"->{
                musicListPlayer = ArrayList()
                musicListPlayer.addAll(MainActivity.musicListMA)
                musicListPlayer.shuffle()
                setLayout()
//                createMediaPlayer()
            }
        }
    }

    //function fro playing the music
    private fun playMusic(){
        binding.btnPlayAndPause.setIconResource(R.drawable.pause_icon)
        isPlaying = true
        musicService!!.mediaPlayer!!.start()
    }
    //function for pausing the music
    private fun pauseMusic(){
        binding.btnPlayAndPause.setIconResource(R.drawable.play_icon)
        isPlaying = false
        musicService!!.mediaPlayer!!.pause()
    }

    private fun prevNextSong(increment: Boolean){
        //if increment is true means we want to go in next song
        //or else we want to go in previous song
        if(increment){
            checkSongPosition(increment = true)
            setLayout()
            createMediaPlayer()
        }else{
            checkSongPosition(increment = false)
            setLayout()
            createMediaPlayer()
        }
    }

    //function for checking and setting up the song position whether it is
    // at first or last position
    private fun checkSongPosition(increment :Boolean){
        if(increment){
            if(musicListPlayer.size-1 == songPosition){
                songPosition = 0
            }else{
                ++songPosition
            }
        }else{
            if(songPosition == 0){
                songPosition = musicListPlayer.size-1
            }else{
                --songPosition
            }
        }
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val binder = service as MusicService.MyBinder
        musicService = binder.currentService()
        createMediaPlayer()
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        musicService = null
    }
}