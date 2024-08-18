package com.bmprj.planner.ui.addTask

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bmprj.planner.R
import com.bmprj.planner.base.BaseFragment
import com.bmprj.planner.databinding.FragmentAddTaskBinding
import com.bmprj.planner.model.Task
import com.bmprj.planner.utils.getDatee
import com.bmprj.planner.utils.setRandomAlarm
import com.bmprj.planner.utils.toast
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddTaskFragment : BaseFragment<FragmentAddTaskBinding>(R.layout.fragment_add_task) {


    private val addTaskViewModel by viewModels<AddTaskViewModel>()

    @RequiresApi(Build.VERSION_CODES.O)
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                continueWithAction()
            } else {
                toast("Bildirim izni verilmedi.")
            }
        }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun continueWithAction() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .build()

        datePicker.show(parentFragmentManager, "tag")

        datePicker.addOnPositiveButtonClickListener {
            binding.dateText.text = it.getDatee()
            setRandomAlarm(it.getDatee())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initView(view: View) {

        initLiveDataObservers()

        with(binding) {
            createTaskButton.setOnClickListener { saveButtonClick() }
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

        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED -> {
                continueWithAction()
            }

            shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                showPermissionExplanation()
            }

            else -> {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun showPermissionExplanation() {
        toast("Bildirim izni verilmemiş ayarlardan bildirimlere izin verin pls.")
    }

    private fun saveButtonClick() {

        with(binding) {
            var marketing = false
            var meeting = false
            var planning = false
            var funn = false
            var other = false

            val checkedCategoryIds = chipGroup.checkedChipIds
            for (i in checkedCategoryIds) {
                when (i) {
                    marketingCategory.id -> {
                        marketing = true
                    }

                    meetingCategory.id -> {
                        meeting = true
                    }

                    planningCategory.id -> {
                        planning = true

                    }

                    funCategory.id -> {
                        funn = true
                    }

                    otherCategory.id -> {
                        other = true
                    }
                }
            }

            val task = Task(
                title = taskTitleEditText.text.toString(),
                description = descriptionEditText.text.toString(),
                isChecked = false,
                taskDate = dateText.text.toString(),
                marketing = marketing,
                meeting = meeting,
                planning = planning,
                funn = funn,
                other = other
            )

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