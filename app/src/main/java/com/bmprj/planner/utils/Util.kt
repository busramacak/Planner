package com.bmprj.planner.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bmprj.planner.databinding.ShareImageLayoutBinding


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


