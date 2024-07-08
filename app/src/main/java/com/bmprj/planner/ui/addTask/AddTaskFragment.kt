package com.bmprj.planner.ui.addTask

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bmprj.planner.R
import com.bmprj.planner.base.BaseFragment
import com.bmprj.planner.databinding.FragmentAddTaskBinding
import com.bmprj.planner.model.Task
import com.bmprj.planner.utils.getDatee
import com.bmprj.planner.utils.setRandomAlarm
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTaskFragment : BaseFragment<FragmentAddTaskBinding>(R.layout.fragment_add_task){

    private val addTaskViewModel by viewModels<AddTaskViewModel>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initView(view: View) {

        initLiveDataObservers()

        with(binding){
            createTaskButton.setOnClickListener{ saveButtonClick()}
            dateButton.setOnClickListener { dateButtonClick() }
            taskBackButton.setOnClickListener { backButtonClick() }
        }
    }

    private fun backButtonClick() {
        val action = AddTaskFragmentDirections.actionAddTaskFragmentToTasksFragment()
        findNavController().navigate(action)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun dateButtonClick() {
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .build()

        datePicker.show(parentFragmentManager,"tag")

        datePicker.addOnPositiveButtonClickListener {
            binding.dateText.text=it.getDatee()
            setRandomAlarm(it.getDatee())
        }
    }

    private fun saveButtonClick() {

        with(binding){
            var marketing =false
            var meeting=false
            var planning=false
            var funn=false
            var other=false

            val checkedCategoryIds = chipGroup.checkedChipIds
            for(i in checkedCategoryIds){
                when(i){
                    marketingCategory.id ->{
                        marketing=true
                    }
                    meetingCategory.id ->{
                        meeting=true
                    }
                    planningCategory.id ->{
                        planning=true

                    }
                    funCategory.id ->{
                        funn=true
                    }
                    otherCategory.id ->{
                        other=true
                    }
                }
            }

            val task = Task(
                title =taskTitleEditText.text.toString(),
                description = descriptionEditText.text.toString(),
                isChecked = false,
                taskDate = dateText.text.toString(),
                marketing = marketing,
                meeting = meeting,
                planning = planning,
                funn = funn,
                other = other)

            addTaskViewModel.insertTask(task)
            val action = AddTaskFragmentDirections.actionAddTaskFragmentToTasksFragment()
            findNavController().navigate(action)
        }

    }

    private fun initLiveDataObservers() {
//        addTaskViewModel.task.handleState(
//            onSucces = {
//                println(it)
//            }
//        )
    }


}