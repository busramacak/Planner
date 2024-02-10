package com.bmprj.planner.utils

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.fragment.app.Fragment

fun Fragment.makeDialog() :AlertDialog.Builder {
    val alertBuilder = AlertDialog.Builder(this.requireContext())
    alertBuilder.setTitle("Değişiklikler Kaydedilmedi.")
    alertBuilder.setMessage("Kaydetmek ister misiniz ?")

    return alertBuilder
}