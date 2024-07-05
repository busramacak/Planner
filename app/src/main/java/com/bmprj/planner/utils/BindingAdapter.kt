package com.bmprj.planner.utils

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Layout
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bmprj.planner.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.imageview.ShapeableImageView


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

fun ShapeableImageView.setPrevIcon(hasText:Boolean){
    this.setImageResource(
        if(hasText){
            R.drawable.icon_prev
        }else{
            R.drawable.icon_prev_flu
        }
    )
}

fun ShapeableImageView.setRedoIcon(hasText:Boolean){
    this.setImageResource(
        if(hasText){
            R.drawable.icon_redo
        }else{
            R.drawable.icon_redo_flu
        }
    )
}

