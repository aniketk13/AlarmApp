package com.example.alarmapp

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
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
import com.example.alarmapp.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object {       //defining media player and alarm manager in companion object so that whole project can access it
        var mp: MediaPlayer? = null
        val notification: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        var alarmManager: AlarmManager? = null
        val time = Calendar.getInstance()
        var i = -1      //defining a variable to generate unique alarms every time the set alarm button is tapped
    }

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        mp = MediaPlayer.create(this, notification)
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        binding.button.setOnClickListener {
            //initializing the i variable with current time only when the button is pressed for the first time
            if (i == -1)
                time.time = Calendar.getInstance().time
            i++

            time.add(Calendar.SECOND, 10)
            Log.i("helloabc", time.time.toString())

            val onlyTime = SimpleDateFormat("h:mm:ss a")    //formatting the time in desired format to display
            Toast.makeText(
                this,
                "Alarm has been set for ${onlyTime.format(time.time)}",
                Toast.LENGTH_SHORT
            ).show()

            setAlarm(time.timeInMillis, i)
        }
    }

    //function which sets the alarm in alarm manager
    private fun setAlarm(timeInMillis: Long, i: Int) {
        val intent = Intent(this, AlarmReceiver::class.java)
        Log.i("helloabc", i.toString())
        val pendingIntent = PendingIntent.getBroadcast(this, i, intent, 0)
        alarmManager?.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent)

    }



}