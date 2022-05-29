package com.rishabhkumar.musicplayer

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.rishabhkumar.musicplayer.databinding.ActivityMainBinding
import java.io.File
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var musicAdapter: MusicAdapter

    companion object{
        lateinit var musicListMA : ArrayList<Music>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeLayout()

        //for checking buttons are working properly or not
        binding.btnShuffle.setOnClickListener {
//            Toast.makeText(this,"Shuffle button clicked.",Toast.LENGTH_SHORT).show()
            val intent = Intent(this@MainActivity, PlayerActivity::class.java)
            startActivity(intent)
        }

        binding.btnFavourite.setOnClickListener {
//            Toast.makeText(this,"Favourite button clicked.",Toast.LENGTH_SHORT).show()
            val intent = Intent(this@MainActivity, FavouriteActivity::class.java)
            startActivity(intent)
        }

        binding.btnPlaylist.setOnClickListener {
//            Toast.makeText(this,"Playlist button clicked.",Toast.LENGTH_SHORT).show()
            val intent = Intent(this@MainActivity, PlaylistActivity::class.java)
            startActivity(intent)
        }

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navFeedback ->
                    Toast.makeText(this, "Feedback clicked.", Toast.LENGTH_SHORT).show()

                R.id.navSettings ->
                    Toast.makeText(this, "Settings clicked.", Toast.LENGTH_SHORT).show()

                R.id.navAbout ->
                    Toast.makeText(this, "About clicked.", Toast.LENGTH_SHORT).show()

                R.id.navExit -> exitProcess(1)
            }
            true
        }

    }

    //for requesting permission
    private fun requestRunTimePermission() {
        if (ActivityCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                11
            )
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 11) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "Permission granted.", Toast.LENGTH_SHORT).show()
            else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    11
                )
            }
        }
    }

    //have to override coz if doesn't override header button will not work
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    //to make code mush easier to read
    @SuppressLint("SetTextI18n")
    private fun initializeLayout() {
        requestRunTimePermission()
        setTheme(R.style.customPinkNav)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //for nav drawer
        toggle = ActionBarDrawerToggle(this, binding.root, R.string.open, R.string.close)
        binding.root.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        val musicList = ArrayList<String>()
//        musicList.add("My name is khan.")
//        musicList.add("Dilli wali girlfriend")
//        musicList.add("Jhagda")
//        musicList.add("8 Parche")
//        musicList.add("Dhadkan")
//        musicList.add("Dulhe ka sehra")
//        musicList.add("On my way")
//        musicList.add("Chandanniya")

        musicListMA = getAllAudio()
        //ram bhot bachata hai
        binding.musicRecyclerView.setHasFixedSize(true)
        binding.musicRecyclerView.setItemViewCacheSize(13)
        binding.musicRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        musicAdapter = MusicAdapter(this@MainActivity, musicListMA)
        binding.musicRecyclerView.adapter = musicAdapter

        binding.txtTotalSongs.text = "Total songs: " + musicAdapter.itemCount


    }


    //code for accessing all audio files (mp3) from phone storage
    @SuppressLint("Recycle", "Range")
    private fun getAllAudio(): ArrayList<Music> {
        val tempList = ArrayList<Music>()
        //cursor help us to access file from storage
        val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.DATE_ADDED,
            MediaStore.Audio.Media.DATA
        )
        val cursor = this.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,null,
            MediaStore.Audio.Media.DATE_ADDED + " DESC",null
        )
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val titleC =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                    val idC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
                    val albumC =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                    val artistC =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                    val durationC =
                        cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                    val pathC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))

                    val music = Music(id = idC,title = titleC,album = albumC,artist = artistC,duration = durationC,path = pathC)
                    val file = File(music.path)
                    if(file.exists()){
                        tempList.add(music)
                    }
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        return tempList
    }
}