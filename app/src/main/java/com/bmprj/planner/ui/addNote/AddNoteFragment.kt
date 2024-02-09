package com.bmprj.planner.ui.addNote

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.bmprj.planner.R
import com.bmprj.planner.base.BaseFragment
import com.bmprj.planner.databinding.FragmentAddNoteBinding
import com.bmprj.planner.model.Note
import com.bmprj.planner.utils.getDateTime
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNoteFragment : BaseFragment<FragmentAddNoteBinding>(R.layout.fragment_add_note) {

    private val addNoteViewModel by viewModels<AddNoteViewModel>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initView(view: View) {
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

}