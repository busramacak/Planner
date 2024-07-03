package com.bmprj.planner.ui.addNote

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bmprj.planner.R
import com.bmprj.planner.base.BaseFragment
import com.bmprj.planner.databinding.FragmentAddNoteBinding
import com.bmprj.planner.databinding.ShareBottomLayoutBinding
import com.bmprj.planner.databinding.ShareImageLayoutBinding
import com.bmprj.planner.model.Note
import com.bmprj.planner.utils.clearFocus
import com.bmprj.planner.utils.getDateTime
import com.bmprj.planner.utils.makeDialog
import com.bmprj.planner.utils.onFocus
import com.bmprj.planner.utils.setDrawable
import com.bmprj.planner.utils.setPrevIcon
import com.bmprj.planner.utils.setRedoIcon
import com.bmprj.planner.utils.setVisibility
import com.bmprj.planner.utils.toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Stack


@AndroidEntryPoint
class AddNoteFragment : BaseFragment<FragmentAddNoteBinding>(R.layout.fragment_add_note) {

    private val addNoteViewModel by viewModels<AddNoteViewModel>()
    private val bundle :AddNoteFragmentArgs by navArgs()
    private val noteId:Int by lazy { bundle.id }
    private lateinit var oldNote:Note
    private lateinit var imageViewResult: ImageView
    @SuppressLint("ClickableViewAccessibility") 
    @RequiresApi(Build.VERSION_CODES.O)
    override fun initView(view: View) {

        initLiveDataObservers()
        if(noteId!=0){
            addNoteViewModel.getNote(noteId)
        }

        with(binding){
            onFocus(content,undoButton,redoButton,shareButton,changeBackgroundButton)
            content.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    if (!content.hasFocus()) {
                        content.requestFocus()
                        content.performClick()
                    } else {
                        onFocus(content,undoButton,redoButton,shareButton,changeBackgroundButton)
                    }
                }
                false
            }
            content.setOnClickListener {   onFocus(content,undoButton,redoButton,shareButton,changeBackgroundButton)}
            content.addTextChangedListener(addNoteViewModel.textWatcher)
            saveButton.setOnClickListener { saveButtonClick() }
            backButton.setOnClickListener { backButtonClick() }
            undoButton.setOnClickListener{ undoButtonClicked() }
            redoButton.setOnClickListener{ redoButtonClicked() }
            shareButton.setOnClickListener{ shareButtonClicked() }
        }

    }

    private fun shareButtonClicked() {
        val bottomSheetBinding = ShareBottomLayoutBinding.inflate(LayoutInflater.from(requireContext()))

        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(bottomSheetBinding.root)

        bottomSheetBinding.btnShareText.setOnClickListener { shareNoteAsText(binding.content.text.toString()) }
        bottomSheetBinding.btnShareImage.setOnClickListener { shareNoteasImage(binding.title.text.toString(),binding.content.text.toString()) }
        bottomSheetBinding.closeButton.setOnClickListener { bottomSheetDialog.dismiss() }
        bottomSheetDialog.show()
    }

    @SuppressLint("Range")
    private fun shareNoteasImage(title:String, content: String) {

        val imageLayoutBinding = ShareImageLayoutBinding.inflate(LayoutInflater.from(requireContext()))

        imageLayoutBinding.title.text=title
        imageLayoutBinding.content.text=content

        val width = 800  // Set your desired width
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        imageLayoutBinding.root.layoutParams=ViewGroup.LayoutParams(width,height)

        val specWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY)
        val specHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.UNSPECIFIED)
        imageLayoutBinding.root.measure(specWidth, specHeight)
        imageLayoutBinding.root.layout(0, 0, imageLayoutBinding.root.measuredWidth, imageLayoutBinding.root.measuredHeight)

        // Create a Bitmap with the same dimensions as the view
        val bitmap = Bitmap.createBitmap(imageLayoutBinding.root.measuredWidth, imageLayoutBinding.root.measuredHeight, Bitmap.Config.ARGB_8888)

        // Create a Canvas and associate it with the Bitmap
        val canvas = Canvas(bitmap)

        // Set background color if necessary
        canvas.drawColor(Color.WHITE)

        // Draw the view on the Canvas
        imageLayoutBinding.root.draw(canvas)

        // Display the created bitmap in ImageView
//        imageViewResult.setImageBitmap(bitmap)

        // Save the bitmap as an image file and share
        saveBitmap(bitmap)
    }

    private fun saveBitmap(bitmap: Bitmap) {
        val fileName = "my_image.png"
        val file = File(requireContext().getExternalFilesDir(null), fileName)
        try {
            val stream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.flush()
            stream.close()
            shareImage(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun shareImage(file: File) {
        val uri = FileProvider.getUriForFile(requireContext(), requireContext().packageName + ".provider", file)
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/png"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivity(Intent.createChooser(shareIntent, "Share image"))
    }

    private fun shareNoteAsText(text:String) {
        if(text.isNotEmpty()){
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, text)
                type = "text/plain"
            }
            val chooser = Intent.createChooser(shareIntent, "Notu paylaş")
            startActivity(chooser)
        }
    }

    private fun redoButtonClicked() {
        addNoteViewModel.redo()
    }

    private fun undoButtonClicked() {
        if(!binding.content.text.isNullOrEmpty()){
            addNoteViewModel.undo()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun backButtonClick() {

        val action = AddNoteFragmentDirections.actionAddNoteFragmentToNotesFragment()

        if(!binding.title.text.isNullOrEmpty() && ::oldNote.isInitialized
            && (binding.title.text.toString() != oldNote.title
                    || binding.content.text.toString() != oldNote.content)){

            val dialog = makeDialog()
            dialog.setPositiveButton("OK"){_,_ ->
                saveButtonClick()
            }
            dialog.setNegativeButton("NO"){_,_ ->
                findNavController().navigate(action)
            }
            dialog.show()
        }else{
            findNavController().navigate(action)
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveButtonClick() {

//        val action = AddNoteFragmentDirections.actionAddNoteFragmentToNotesFragment()
        with(binding){
            if(noteId!=0){
                val note = Note(noteId = noteId,
                    title = title.text.toString(),
                    content = content.text.toString(),
                    date = getDateTime())
                addNoteViewModel.updateNote(note)
            }else{
                val note = Note(
                    title = title.text.toString(),
                    content = content.text.toString(),
                    date = getDateTime())
                addNoteViewModel.insertNote(note)
            }
//            shareButton.setVisibility(true)
//            changeBackgroundButton.setVisibility(true)
//            findNavController().navigate(action)
        }

    }

    private fun initLiveDataObservers() {
        with(binding) {
            addNoteViewModel.notee.handleState(
                onSucces = {
                    oldNote=it

                    title.setText(it.title)
                    content.setText(it.content)
                    date.text = it.date
//                    onFocus(content,undoButton,redoButton)

                }
            )
            addNoteViewModel.insert.handleState(
                onSucces = {
                    toast("not başarıyla kaydedildi.")
                    clearFocus(content,undoButton,redoButton,shareButton,changeBackgroundButton)
                }
            )

            addNoteViewModel.updatee.handleState(
                onSucces = {
                    toast("not başarıyla güncellendi.")
                    clearFocus(content,undoButton,redoButton,shareButton,changeBackgroundButton)
                }
            )

            addNoteViewModel.characterLength.handleStateT(
                onSucces = {
                    characters.text=getString(R.string.characters,it.toString())
                }
            )

            addNoteViewModel.textState.handleStateT(
                onSucces = {
                    binding.redoButton.setRedoIcon(addNoteViewModel.redoList.isNotEmpty())
                    undoButton.setPrevIcon(it != "")
                    content.removeTextChangedListener(addNoteViewModel.textWatcher)
                    content.setText(it)
                    content.setSelection(it.length)
                    content.addTextChangedListener(addNoteViewModel.textWatcher)
                }
            )
        }
    }

}