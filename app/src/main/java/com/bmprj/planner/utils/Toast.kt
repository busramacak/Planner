package com.bmprj.planner.utils

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment


fun Fragment.toast(content:String) = toast(requireContext(),content)
fun Fragment.toastLong(content:String) = toastLong(requireContext(),content)


private fun toast(context: Context,content:String, duration:Int = Toast.LENGTH_SHORT){
    Toast.makeText(context,content,duration).show()
}

private fun toastLong(context: Context,content: String) = toast(context,content, Toast.LENGTH_LONG)