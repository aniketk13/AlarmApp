package com.example.alarmapp

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.SystemClock
import android.text.format.DateUtils
import android.util.Log
import androidx.core.app.AlarmManagerCompat
import androidx.core.content.ContextCompat
import com.example.alarmapp.MainActivity.Companion.alarmManager
import com.example.alarmapp.MainActivity.Companion.mp
import com.example.alarmapp.MainActivity.Companion.time
import java.util.*
import kotlin.time.Duration.Companion.minutes


class SnoozeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, p1: Intent?) {
        mp?.stop()
        mp?.reset()
        mp?.setDataSource(context!!, MainActivity.notification)
        mp?.prepare()
        Log.i("helloabc",mp.toString())

//        val triggerTime=Calendar.getInstance()
//        time.time=Calendar.getInstance().time
        time.add(Calendar.SECOND,5)

        Log.i("helloabc", time.time.toString())

        val notifyIntent = Intent(context, AlarmReceiver::class.java)
        val notifyPendingIntent =
            PendingIntent.getBroadcast(context, 0, notifyIntent, 0)

        alarmManager?.setExact(AlarmManager.RTC_WAKEUP,time.timeInMillis,notifyPendingIntent)

        val notificationManager = ContextCompat.getSystemService(
            context!!,
            NotificationManager::class.java
        ) as NotificationManager
        notificationManager.cancelAll()
    }
}