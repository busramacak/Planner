package com.bmprj.planner.ui.addTask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bmprj.planner.R
import com.bmprj.planner.base.BaseFragment
import com.bmprj.planner.databinding.FragmentAddTaskBinding
import com.bmprj.planner.model.Category
import com.bmprj.planner.model.Task
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTaskFragment() : BaseFragment<FragmentAddTaskBinding>(R.layout.fragment_add_task){

    private val addTaskViewModel by viewModels<AddTaskViewModel>()

    override fun initView(view: View) {

        initLiveDataObservers()

        with(binding){

            createTaskButton.setOnClickListener{ saveButtonClick()}
        }
    }

    private fun saveButtonClick() {

        with(binding){
            val categoryList= arrayListOf<Category>()
            val checkedCategoryIds = chipGroup.checkedChipIds
            for(i in checkedCategoryIds){
                when(i){
                    marketingCategory.id ->{
                        categoryList.add(Category(i,marketingCategory.text.toString()))
                    }
                    meetingCategory.id ->{
                        categoryList.add(Category(i,meetingCategory.text.toString()))
                    }
                    planningCategory.id ->{
                        categoryList.add(Category(i,planningCategory.text.toString()))
                    }
                    funCategory.id ->{
                        categoryList.add(Category(i,funCategory.text.toString()))
                    }
                    otherCategory.id ->{
                        categoryList.add(Category(i,otherCategory.text.toString()))
                    }
                }
            }

            val task = Task(
                title =taskTitleEditText.text.toString(),
                description = descriptionEditText.text.toString(),
                taskDate = "13/15/2024",
                taskTime = "13:15",
                category = categoryList)

            addTaskViewModel.insertTask(task)
        }

    }

    private fun initLiveDataObservers() {

    }


}