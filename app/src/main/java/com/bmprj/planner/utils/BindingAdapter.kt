package com.bmprj.planner.utils

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bmprj.planner.R


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
        this.visibility=View.GONE
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