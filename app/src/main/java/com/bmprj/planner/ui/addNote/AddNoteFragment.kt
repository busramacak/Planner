package com.bmprj.planner.ui.addNote

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bmprj.planner.R
import com.bmprj.planner.base.BaseFragment
import com.bmprj.planner.databinding.FragmentAddNoteBinding
import com.bmprj.planner.model.Note
import com.bmprj.planner.utils.getDateTime
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNoteFragment : BaseFragment<FragmentAddNoteBinding>(R.layout.fragment_add_note) {

    private val addNoteViewModel by viewModels<AddNoteViewModel>()
    private val bundle :AddNoteFragmentArgs by navArgs()
    private val noteId:Int by lazy { bundle.id }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun initView(view: View) {

        initLiveDataObservers()
        if(noteId!=0){
            addNoteViewModel.getNote(noteId)
        }

        with(binding){
            materialButton.setOnClickListener {
                val note = Note(
                    title = title.text.toString(),
                    content = content.text.toString(),
                    date = getDateTime())
                addNoteViewModel.insertNote(note)
            }
        }
    }

    private fun initLiveDataObservers() {
        addNoteViewModel.notee.handleState(
            onSucces = {}
        )
    }

}