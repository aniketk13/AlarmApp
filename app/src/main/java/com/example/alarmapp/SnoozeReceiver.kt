package com.example.alarmapp

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.alarmapp.MainActivity.Companion.alarmManager
import com.example.alarmapp.MainActivity.Companion.mp
import com.example.alarmapp.MainActivity.Companion.time
import java.util.*


class SnoozeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, p1: Intent?) {

        //restarting media player as it cannot start again after stopping
        mp?.stop()
        mp?.reset()
        mp?.setDataSource(context!!, MainActivity.notification)
        mp?.prepare()

        time.add(Calendar.SECOND, 5)    //new alarm is being scheduled after 5 sec from the old alarm

        Log.i("helloabc", time.time.toString())

        val notifyIntent = Intent(context, AlarmReceiver::class.java)
        val notifyPendingIntent =
            PendingIntent.getBroadcast(context, 0, notifyIntent, 0)

        alarmManager?.setExact(AlarmManager.RTC_WAKEUP, time.timeInMillis, notifyPendingIntent)

        val notificationManager = ContextCompat.getSystemService(
            context!!,
            NotificationManager::class.java
        ) as NotificationManager
        notificationManager.cancelAll()     //cancelling the current notifications on snooze
    }
}