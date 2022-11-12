package com.example.alarmapp

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.alarmapp.MainActivity.Companion.alarmManager
import com.example.alarmapp.MainActivity.Companion.mp
import com.example.alarmapp.MainActivity.Companion.notification

class StopAlarm:BroadcastReceiver() {
    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onReceive(context: Context?, p1: Intent?) {
        Log.i("helloabc","stop")
        mp?.stop()
        mp?.reset()
        mp?.setDataSource(context!!, notification)
        mp?.prepare()
        
        val notificationManager = ContextCompat.getSystemService(
            context!!,
            NotificationManager::class.java
        ) as NotificationManager
        notificationManager.cancelAll()
    }
}