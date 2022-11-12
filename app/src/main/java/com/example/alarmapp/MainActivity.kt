package com.example.alarmapp

import android.app.*
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat.EXTRA_NOTIFICATION_ID
import com.example.alarmapp.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    companion object{       //defining media player and alarm manager in companion object so that whole project can access it
        var mp: MediaPlayer? =null
        val notification: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        var alarmManager:AlarmManager?=null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        mp=MediaPlayer.create(this, notification)

        binding.button.setOnClickListener {
            val time2 = Calendar.getInstance().time
            val time = Calendar.getInstance()
            time.time = time2
            time.add(Calendar.SECOND, 5)
            Log.i("helloabc", time.time.toString())
            setAlarm(time.timeInMillis)
        }
        createNotificationChannel()
    }

    private fun setAlarm(timeInMillis: Long) {
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
        alarmManager?.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "myAlarm"
            val description = "Alarm App"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("myalarm", name, importance)
            channel.description = description
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)

        }
    }


}