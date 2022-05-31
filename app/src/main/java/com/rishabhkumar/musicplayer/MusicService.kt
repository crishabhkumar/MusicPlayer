package com.rishabhkumar.musicplayer

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder

class MusicService: Service() {
    private var myBinder = MyBinder()
    var mediaPlayer : MediaPlayer? = null

    override fun onBind(intent: Intent?): IBinder {
        return myBinder
    }

    //will help in return main class object
    //will return current music object in musicService
    inner class MyBinder: Binder(){
        fun currentService(): MusicService {
            return this@MusicService
        }
    }
}