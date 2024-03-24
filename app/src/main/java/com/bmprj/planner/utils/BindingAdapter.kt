package com.bmprj.planner.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter


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