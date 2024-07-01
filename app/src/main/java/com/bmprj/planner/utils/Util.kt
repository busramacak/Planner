package com.bmprj.planner.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment



fun Fragment.onFocus(editText: EditText,undoButton:ImageView,redoButton:ImageView,shareButton:ImageView,changeBackgroundButton:ImageView){
    editText.requestFocus()
    undoButton.visibility= View.VISIBLE
    redoButton.visibility= View.VISIBLE
    shareButton.visibility=View.GONE
    changeBackgroundButton.visibility=View.GONE
    editText.post {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
        editText.setSelection(editText.text.length) // Move the cursor to the end
    }
}

fun Fragment.clearFocus(editText: EditText,undoButton:ImageView,redoButton:ImageView,shareButton:ImageView,changeBackgroundButton:ImageView){
    editText.clearFocus()
    undoButton.visibility=View.GONE
    redoButton.visibility=View.GONE
    shareButton.visibility= View.VISIBLE
    changeBackgroundButton.visibility= View.VISIBLE
    editText.post {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(editText.windowToken, 0)
    }
}

