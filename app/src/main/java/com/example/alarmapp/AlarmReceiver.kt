package com.example.alarmapp

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.alarmapp.MainActivity.Companion.mp


class AlarmReceiver : BroadcastReceiver() {
    var contextNew: Context? = null
    var snoozePendingIntent: PendingIntent? = null
    var stopPendingIntent: PendingIntent? = null

    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onReceive(context: Context?, intent: Intent?) {
        mp?.start()     //starts the media player as soon as the alarm goes off
        Log.i("helloabc", mp.toString())
        contextNew = context

        intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK


        val i = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, i, 0)

        val snoozeIntent = snoozeAlarm()
        snoozePendingIntent =
            PendingIntent.getBroadcast(context, 0, snoozeIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val stopIntent = stopAlarm()
        stopPendingIntent =
            PendingIntent.getBroadcast(context, 0, stopIntent, PendingIntent.FLAG_CANCEL_CURRENT)

        //building the notification architecture
        val builder = NotificationCompat.Builder(context!!, "myalarm")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Alarm Ringing...")
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.ic_snooze, "Snooze", snoozePendingIntent)
            .addAction(R.drawable.ic_stop, "Dismiss", stopPendingIntent)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(123, builder.build())
        Log.i("helloabc", "ring")

    }

    private fun snoozeAlarm(): Intent {
        val snoozeIntent = Intent(contextNew, SnoozeReceiver::class.java).apply {
            this.action = "android.intent.action.SNOOZE_ALARM"
            Log.i("helloabc", "snooze")
            putExtra(NotificationCompat.EXTRA_NOTIFICATION_ID, 0)
        }
        return snoozeIntent
    }

    private fun stopAlarm(): Intent {
        val stopIntent = Intent(contextNew, StopAlarm::class.java).apply {
            this.action = "android.intent.action.DISMISS_ALARM"
        }
        return stopIntent
    }
}
