package com.bmprj.planner.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment


fun Fragment.onFocus(editText: EditText){
    editText.requestFocus()
    editText.post {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
        editText.setSelection(editText.text.length) // Move the cursor to the end
    }
}

fun Fragment.clearFocus(editText: EditText){
    editText.clearFocus()
    editText.post {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(editText.windowToken, 0)
    }
}