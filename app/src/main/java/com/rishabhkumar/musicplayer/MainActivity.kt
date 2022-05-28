package com.rishabhkumar.musicplayer

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rishabhkumar.musicplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MusicPlayer)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //for checking buttons are working properly or not
        binding.btnShuffle.setOnClickListener{
            Toast.makeText(this,"Shuffle button clicked.",Toast.LENGTH_SHORT).show()
        }

        binding.btnFavourite.setOnClickListener{
            Toast.makeText(this,"Favourite button clicked.",Toast.LENGTH_SHORT).show()
        }

        binding.btnPlaylist.setOnClickListener{
            Toast.makeText(this,"Playlist button clicked.",Toast.LENGTH_SHORT).show()
        }
    }
}