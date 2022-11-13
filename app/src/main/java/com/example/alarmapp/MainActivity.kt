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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat.EXTRA_NOTIFICATION_ID
import com.example.alarmapp.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    companion object{       //defining media player and alarm manager in companion object so that whole project can access it
        var mp: MediaPlayer? =null
        val notification: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        var alarmManager:AlarmManager?=null
        val time=Calendar.getInstance()
        var i=-1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        mp=MediaPlayer.create(this, notification)


        binding.button.setOnClickListener {
            if(i==-1)
                time.time=Calendar.getInstance().time
            i++
            time.add(Calendar.SECOND, 10)
            Log.i("helloabc", time.time.toString())
            val onlyTime=SimpleDateFormat("h:mm:ss a")
            Toast.makeText(this,"Alarm has been set for ${onlyTime.format(time.time)}",Toast.LENGTH_SHORT).show()
            setAlarm(time.timeInMillis,i)
        }
        createNotificationChannel()
    }

    private fun setAlarm(timeInMillis: Long,i:Int) {
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        Log.i("helloabc",i.toString())
        val pendingIntent = PendingIntent.getBroadcast(this, i, intent, 0)
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