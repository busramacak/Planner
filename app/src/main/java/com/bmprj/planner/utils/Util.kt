package com.bmprj.planner.utils

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bmprj.planner.databinding.ShareImageLayoutBinding
import java.util.Calendar


fun Fragment.onFocus(
    editText: EditText,
    undoButton: ImageView,
    redoButton: ImageView,
    shareButton: ImageView,
    saveButton: ImageView
) {
    editText.requestFocus()
    undoButton.visibility = View.VISIBLE
    redoButton.visibility = View.VISIBLE
    saveButton.visibility = View.VISIBLE
    shareButton.visibility = View.GONE

    editText.post {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
        editText.setSelection(editText.text.length) // Move the cursor to the end
    }
}

fun Fragment.clearFocus(
    editText: EditText,
    undoButton: ImageView,
    redoButton: ImageView,
    shareButton: ImageView,
    saveButton: ImageView
) {
    editText.clearFocus()
    undoButton.visibility = View.GONE
    redoButton.visibility = View.GONE
    saveButton.visibility = View.GONE
    shareButton.visibility = View.VISIBLE
    editText.post {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(editText.windowToken, 0)
    }
}

fun Fragment.changeColor(
    layout: ShareImageLayoutBinding,
    layoutXml: ShareImageLayoutBinding,
    firstColor: Int,
    secondColor: Int,
    shareButton: Button,
) {
    layout.linearLayout.setBackgroundColor(resources.getColor(firstColor))
    layout.title.setBackgroundColor(resources.getColor(secondColor))
    layout.content.setBackgroundColor(resources.getColor(secondColor))
    layout.plannerText.setBackgroundColor(resources.getColor(secondColor))
    layoutXml.linearLayout.setBackgroundColor(resources.getColor(firstColor))
    layoutXml.title.setBackgroundColor(resources.getColor(secondColor))
    layoutXml.content.setBackgroundColor(resources.getColor(secondColor))
    layoutXml.plannerText.setBackgroundColor(resources.getColor(secondColor))
    shareButton.setBackgroundColor(resources.getColor(secondColor))
}


@SuppressLint("ScheduleExactAlarm")
fun Fragment.setRandomAlarm(date:String) {
    val calendar = Calendar.getInstance()
    val (day, month, year) = splitDate(date)
    val startHour: Int=14
    val endHour: Int = 14
    calendar.set(Calendar.YEAR, year)
    calendar.set(Calendar.MONTH, month - 1) // Aylar 0 tabanlıdır.
    calendar.set(Calendar.DAY_OF_MONTH, day)

    val randomHour = (startHour..endHour).random()
    val randomMinute = (17..20).random()
    println(randomMinute)

    calendar.set(Calendar.HOUR_OF_DAY, randomHour)
    calendar.set(Calendar.MINUTE, randomMinute)
    calendar.set(Calendar.SECOND, 0)

    val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(requireContext(), AlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
}