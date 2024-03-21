package com.bmprj.planner.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun getDateTime() = getDateTimes()

@RequiresApi(Build.VERSION_CODES.O)
fun Long.getDatee() = getDate(this)

@RequiresApi(Build.VERSION_CODES.O)
private fun getDateTimes():String{
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yy | HH:mm")
    val current = LocalDateTime.now().format(formatter)
    return current
}
@RequiresApi(Build.VERSION_CODES.O)
private fun getDate(l: Long):String{
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
    val inst = Instant.ofEpochMilli(l)
    val date = LocalDateTime.ofInstant(inst, ZoneId.systemDefault()).format(formatter)
    return date
}