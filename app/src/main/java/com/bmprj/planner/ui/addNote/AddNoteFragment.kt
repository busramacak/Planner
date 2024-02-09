package com.bmprj.planner.ui.addNote

import android.view.View
import androidx.fragment.app.viewModels
import com.bmprj.planner.R
import com.bmprj.planner.base.BaseFragment
import com.bmprj.planner.data.Entity
import com.bmprj.planner.databinding.FragmentAddNoteBinding
import com.bmprj.planner.model.Note
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNoteFragment : BaseFragment<FragmentAddNoteBinding>(R.layout.fragment_add_note) {

    private val addNoteViewModel by viewModels<AddNoteViewModel>()

    override fun initView(view: View) {
        binding.materialButton.setOnClickListener {
            val note = Entity(title = binding.title.text.toString(), content = binding.content.text.toString(), date = "2222")
            addNoteViewModel.insertNote(note)
        }
    }

}