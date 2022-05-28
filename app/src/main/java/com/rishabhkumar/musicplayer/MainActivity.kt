package com.rishabhkumar.musicplayer

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.rishabhkumar.musicplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestRunTimePermission()
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
        requestRunTimePermission()

    }
    //for requesting permission
    private fun requestRunTimePermission(){
        if(ActivityCompat.checkSelfPermission(this@MainActivity,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),11)
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == 11){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this,"Permission granted.",Toast.LENGTH_SHORT).show()
            else{
                ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),11)
            }
        }

    }
}