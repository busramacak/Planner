package com.bmprj.planner.utils

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bmprj.planner.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


@BindingAdapter("setVisibility")
fun TextView.setVisibility(isSelected:Boolean){
    if(isSelected){
        this.visibility= View.VISIBLE
    }else {
        this.visibility=View.GONE
    }
}

@BindingAdapter("setVisibility")
fun ImageView.setVisibility(content:String){
    if(content==""){
        this.visibility=View.INVISIBLE
    }else{
        this.visibility=View.VISIBLE
    }
}

@BindingAdapter("setDrawable")
fun TextView.setDrawable(isComplete:Boolean){
    this.foreground=if(isComplete){
        resources.getDrawable(R.drawable.line)
    }else{
        resources.getDrawable(R.drawable.not_line)
    }
}

fun FloatingActionButton.setDrawable(currentFragment:String){
    val iconResId = when(currentFragment){
        "note" -> R.drawable.icon_add_note
        "task" -> R.drawable.icon_add_task
        else -> R.drawable.icon_add
    }
    this.setImageResource(iconResId)
}