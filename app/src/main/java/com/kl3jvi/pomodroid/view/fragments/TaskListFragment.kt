package com.kl3jvi.pomodroid.view.fragments

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.kl3jvi.pomodroid.application.PomodoroApplication
import com.kl3jvi.pomodroid.databinding.FragmentTaskListBinding
import com.kl3jvi.pomodroid.model.entities.Task
import com.kl3jvi.pomodroid.view.adapters.TaskAdapter
import com.kl3jvi.pomodroid.viewmodel.TaskViewModel
import com.kl3jvi.pomodroid.viewmodel.TaskViewModelFactory
import com.maxkeppeler.sheets.info.InfoSheet
import java.util.*

class TaskListFragment : Fragment() {

    private lateinit var mBinding: FragmentTaskListBinding
    private lateinit var mTaskAdapter: TaskAdapter

    private val mTaskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory((requireActivity().application as PomodoroApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentTaskListBinding.inflate(inflater, container, false)
        val root: View = mBinding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.rvTaskList.layoutManager = GridLayoutManager(requireActivity(), 1)
        mTaskAdapter = TaskAdapter(this@TaskListFragment)
        mBinding.rvTaskList.adapter = mTaskAdapter

        val it = mTaskViewModel.allTaskList
        mTaskAdapter.tasksList(it)

    }

    fun getRandomColor(): ColorStateList {
        val color: Int = Color.argb(255, 0, 144, 193)
        return ColorStateList.valueOf(color)
    }

    fun deleteWithoutAlert(task: Task){
        mTaskViewModel.delete(task)
    }

    fun deleteTask(task: Task) {
        InfoSheet().show(requireContext()) {
            title("Vazifani o'chirmoqchimisiz?")
            content("Vazifa ma'lumotlar bazasidan butunlay o'chib ketadi")
            onNegative("Yo'q") {
                // Handle event
                dismiss()
            }
            onPositive("O'chirish") {
                mTaskViewModel.delete(task)
                Toast.makeText(requireContext(), "Vazifa muvaffaqiyatli o'chirildi", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}