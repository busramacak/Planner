package com.bmprj.planner

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.navigation.fragment.navArgs
import com.bmprj.planner.base.BaseFragment
import com.bmprj.planner.databinding.FragmentShowNoteBinding
import com.bmprj.planner.ui.addNote.AddNoteFragmentArgs

class ShowNoteFragment : BaseFragment<FragmentShowNoteBinding>(R.layout.fragment_show_note) {

    private val bundle: ShowNoteFragmentArgs by navArgs()
    private val title: String by lazy { bundle.title }
    private val content: String by lazy { bundle.content }

    override fun initView(view: View) {
        binding.imageViewLayout.title.text=title
        binding.imageViewLayout.content.text=content
        binding.changeBackgroundButton.setOnClickListener { changeBackgroundButtonClicked(it) }
    }

    private fun changeBackgroundButtonClicked(view: View) {
        val popup = PopupMenu(requireContext(), view)
        popup.menuInflater.inflate(R.menu.background_menu, popup.menu)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            popup.setForceShowIcon(true)
        }
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.blue -> {
                    binding.imageViewLayout.linearLayout.setBackgroundColor(resources.getColor(R.color.redFlue))
                    binding.imageViewLayout.title.setBackgroundColor(resources.getColor(R.color.red))
                    binding.imageViewLayout.content.setBackgroundColor(resources.getColor(R.color.red))
                    binding.imageViewLayout.plannerText.setBackgroundColor(resources.getColor(R.color.red))
                    true
                }

                R.id.green -> {
                    println("green")
                    true
                }

                else -> false
            }
        }
        popup.show()

    }
}