package com.bmprj.planner.ui.addNote

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bmprj.planner.R
import com.bmprj.planner.base.BaseFragment
import com.bmprj.planner.databinding.FragmentAddNoteBinding
import com.bmprj.planner.databinding.ShareBottomLayoutBinding
import com.bmprj.planner.model.Note
import com.bmprj.planner.utils.clearFocus
import com.bmprj.planner.utils.getDateTime
import com.bmprj.planner.utils.makeDialog
import com.bmprj.planner.utils.onFocus
import com.bmprj.planner.utils.setPrevIcon
import com.bmprj.planner.utils.setRedoIcon
import com.bmprj.planner.utils.toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddNoteFragment : BaseFragment<FragmentAddNoteBinding>(R.layout.fragment_add_note) {

    private val addNoteViewModel by viewModels<AddNoteViewModel>()
    private val bundle: AddNoteFragmentArgs by navArgs()
    private val noteId: Int by lazy { bundle.id }
    private lateinit var oldNote: Note
    private lateinit var imageViewResult: ImageView

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ClickableViewAccessibility")
    override fun initView(view: View) {

        initLiveDataObservers()
        if (noteId != 0) {
            addNoteViewModel.getNote(noteId)
        }

        with(binding) {
            onFocus(content, undoButton, redoButton, shareButton,saveButton)
            content.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    if (!content.hasFocus()) {
                        content.requestFocus()
                        content.performClick()
                    } else {
                        onFocus(
                            content,
                            undoButton,
                            redoButton,
                            shareButton,
                            saveButton
                        )
                    }
                }
                false
            }
            content.setOnClickListener {
                onFocus(
                    content,
                    undoButton,
                    redoButton,
                    shareButton,
                    saveButton
                )
            }
            content.addTextChangedListener(addNoteViewModel.textWatcher)
            saveButton.setOnClickListener { saveButtonClick() }
            backButton.setOnClickListener { backButtonClick() }
            undoButton.setOnClickListener { undoButtonClicked() }
            redoButton.setOnClickListener { redoButtonClicked() }
            shareButton.setOnClickListener { shareButtonClicked() }
        }

    }


    private fun shareButtonClicked() {
        val bottomSheetBinding =
            ShareBottomLayoutBinding.inflate(LayoutInflater.from(requireContext()))

        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(bottomSheetBinding.root)

        bottomSheetBinding.btnShareText.setOnClickListener { shareNoteAsText(binding.content.text.toString()) }
        bottomSheetBinding.btnShareImage.setOnClickListener {
            shareNoteAsImage(
                binding.title.text.toString(),
                binding.content.text.toString()
            )
            bottomSheetDialog.dismiss()
        }
        bottomSheetBinding.closeButton.setOnClickListener { bottomSheetDialog.dismiss() }
        bottomSheetDialog.show()
    }

    @SuppressLint("Range")
    private fun shareNoteAsImage(title: String, content: String) {
        val action =
            AddNoteFragmentDirections.actionAddNoteFragmentToShowNoteFragment(title, content)
        findNavController().navigate(action)
    }

    private fun shareNoteAsText(text: String) {
        if (text.isNotEmpty()) {
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
        if (!binding.content.text.isNullOrEmpty()) {
            addNoteViewModel.undo()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun backButtonClick() {

        val action = AddNoteFragmentDirections.actionAddNoteFragmentToNotesFragment()

        if (!binding.title.text.isNullOrEmpty() && ::oldNote.isInitialized
            && (binding.title.text.toString() != oldNote.title
                    || binding.content.text.toString() != oldNote.content)
        ) {

            val dialog = makeDialog()
            dialog.setPositiveButton("OK") { _, _ ->
                saveButtonClick()
            }
            dialog.setNegativeButton("NO") { _, _ ->
                findNavController().navigate(action)
            }
            dialog.show()
        } else {
            findNavController().navigate(action)
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveButtonClick() {

//        val action = AddNoteFragmentDirections.actionAddNoteFragmentToNotesFragment()
        with(binding) {
            if (noteId != 0) {
                if(oldNote.content != content.text.toString()){
                    val note = Note(
                        noteId = noteId,
                        title = title.text.toString(),
                        content = content.text.toString(),
                        date = getDateTime()
                    )
                    addNoteViewModel.updateNote(note)
                } else {
                    clearFocus(content, undoButton, redoButton, shareButton,saveButton)
                }

            } else {
                val note = Note(
                    title = title.text.toString(),
                    content = content.text.toString(),
                    date = getDateTime()
                )
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
                    oldNote = it

                    title.setText(it.title)
                    content.setText(it.content)
                    date.text = it.date

                }
            )
            addNoteViewModel.insert.handleState(
                onSucces = {
                    toast("not başarıyla kaydedildi.")
                    clearFocus(content, undoButton, redoButton, shareButton,saveButton)
                }
            )

            addNoteViewModel.updatee.handleState(
                onSucces = {
                    toast("not başarıyla güncellendi.")
                    clearFocus(content, undoButton, redoButton, shareButton,saveButton)
                }
            )

            addNoteViewModel.characterLength.handleStateT(
                onSucces = {
                    characters.text = getString(R.string.characters, it.toString())
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