package com.bmprj.planner.utils

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun getDateTime() = getDateTimes()


@RequiresApi(Build.VERSION_CODES.O)
private fun getDateTimes():String{
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yy | HH:mm")
    val current = LocalDateTime.now().format(formatter)
    return current
}