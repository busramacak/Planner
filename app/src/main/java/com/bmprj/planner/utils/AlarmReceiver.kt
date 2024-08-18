package com.bmprj.planner.utils

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bmprj.planner.R


class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
      showNotification(p0)
    }

    private fun showNotification(p0: Context?) {

        val notificationManager = p0?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationBuilder = NotificationCompat.Builder(p0, "channel_id")
            .setSmallIcon(R.drawable.icon_alarm)
            .setContentTitle("Alarm Notification")
            .setContentText("This is your alarm notification!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("channel_id", "Channel Name", NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = "Channel Description"
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(1, notificationBuilder.build())

    }
}
