package com.bmprj.planner.ui.task

import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bmprj.planner.R
import com.bmprj.planner.base.BaseFragment
import com.bmprj.planner.databinding.FragmentTasksBinding
import com.bmprj.planner.model.Task
import com.bmprj.planner.ui.MainViewModel
import com.bmprj.planner.utils.makeDialogForDelete
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TasksFragment : BaseFragment<FragmentTasksBinding>(R.layout.fragment_tasks) {
    private val taskAdapter by lazy { TaskAdapter(::onAlarmClicked,::onTaskLongClicked, ::onCompleteClicked, ::onDeleteClicked) }
    private val taskViewModel by viewModels<TaskViewModel>()
    private val sharedViewModel by activityViewModels<MainViewModel>()
    override fun initView(view: View) {

        taskViewModel.getAllTasks()
        initLiveDataObservers()
        initAdapter()
        with(binding){
//            addTaskButton.setOnClickListener{ addTaskClicked()}
        }




    }

    private fun initAdapter() {
        with(binding){
            taskListRecyclerView.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            taskListRecyclerView.adapter = taskAdapter
        }
    }

    private fun initLiveDataObservers() {
        taskViewModel.taskList.handleState(
            onSucces = {
                taskAdapter.updateList(it)
            }
        )
        viewLifecycleOwner.lifecycleScope.launch {
            sharedViewModel.fabClickEvent.collect { isClicked ->
                if (isClicked) {
                    addTaskClicked()
                    sharedViewModel.makeFabValueDefault()
                }
            }
        }
    }

    private fun onTaskLongClicked(task: Task): Boolean {
        println("uzun tiklandi")
        return false
    }
    private fun onCompleteClicked(task: Task) {
        taskViewModel.updateTask(task)
    }
    private fun onAlarmClicked(task: Task) {
        Toast.makeText(requireContext(),task.taskDate + " " +task.taskTime, Toast.LENGTH_LONG).show()
    }
    private fun onDeleteClicked(task: Task) {
        val alertDialog = makeDialogForDelete()
        alertDialog.setPositiveButton("Ok"){ _,_ ->
            taskViewModel.deleteTask(task)
            taskAdapter.list.remove(task)
            taskAdapter.notifyDataSetChanged()
        }
        alertDialog.setNegativeButton("No"){_,_ ->

        }
        alertDialog.show()

    }
    private fun addTaskClicked() {
        val action = TasksFragmentDirections.actionTasksFragmentToAddTaskFragment()
        findNavController().navigate(action)
    }
}