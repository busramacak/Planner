package com.bmprj.planner.ui.addNote

import android.content.Context
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bmprj.planner.R
import com.bmprj.planner.base.BaseFragment
import com.bmprj.planner.databinding.FragmentAddNoteBinding
import com.bmprj.planner.model.Note
import com.bmprj.planner.utils.clearFocus
import com.bmprj.planner.utils.getDateTime
import com.bmprj.planner.utils.makeDialog
import com.bmprj.planner.utils.onFocus
import com.bmprj.planner.utils.setDrawable
import com.bmprj.planner.utils.setPrevIcon
import com.bmprj.planner.utils.setRedoIcon
import com.bmprj.planner.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import java.util.Stack


@AndroidEntryPoint
class AddNoteFragment : BaseFragment<FragmentAddNoteBinding>(R.layout.fragment_add_note) {

    private val addNoteViewModel by viewModels<AddNoteViewModel>()
    private val bundle :AddNoteFragmentArgs by navArgs()
    private val noteId:Int by lazy { bundle.id }
    private lateinit var oldNote:Note
    @RequiresApi(Build.VERSION_CODES.O)
    override fun initView(view: View) {

        initLiveDataObservers()
        if(noteId!=0){
            addNoteViewModel.getNote(noteId)
        }

        with(binding){
            onFocus(content)
            content.addTextChangedListener(addNoteViewModel.textWatcher)
            saveButton.setOnClickListener { saveButtonClick() }
            backButton.setOnClickListener { backButtonClick() }
            undoButton.setOnClickListener{ undoButtonClicked() }
            redoButton.setOnClickListener{ redoButtonClicked()}
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
                    onFocus(content)

                }
            )
            addNoteViewModel.insert.handleState(
                onSucces = {
                    toast("not başarıyla kaydedildi.")
                    clearFocus(content)
                }
            )

            addNoteViewModel.updatee.handleState(
                onSucces = {
                    toast("not başarıyla güncellendi.")
                    clearFocus(content)
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