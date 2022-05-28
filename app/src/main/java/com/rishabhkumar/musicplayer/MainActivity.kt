package com.rishabhkumar.musicplayer

import android.content.Intent
import android.os.Bundle
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
//            Toast.makeText(this,"Shuffle button clicked.",Toast.LENGTH_SHORT).show()
            val intent = Intent(this@MainActivity, PlayerActivity::class.java)
            startActivity(intent)
        }

        binding.btnFavourite.setOnClickListener{
//            Toast.makeText(this,"Favourite button clicked.",Toast.LENGTH_SHORT).show()
            val intent = Intent(this@MainActivity, FavouriteActivity::class.java)
            startActivity(intent)
        }

        binding.btnPlaylist.setOnClickListener{
//            Toast.makeText(this,"Playlist button clicked.",Toast.LENGTH_SHORT).show()
            val intent = Intent(this@MainActivity,PlaylistActivity::class.java)
            startActivity(intent)
        }

    }
}