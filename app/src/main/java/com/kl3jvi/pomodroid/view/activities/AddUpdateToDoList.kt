package com.kl3jvi.pomodroid.view.activities

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

import com.kl3jvi.pomodroid.R
import com.kl3jvi.pomodroid.application.PomodoroApplication
import com.kl3jvi.pomodroid.databinding.ActivityAddUpdateToDoListBinding
import com.kl3jvi.pomodroid.model.entities.Task
import com.kl3jvi.pomodroid.viewmodel.TaskViewModel
import com.kl3jvi.pomodroid.viewmodel.TaskViewModelFactory
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class AddUpdateToDoList : AppCompatActivity(){

    private lateinit var mBinding: ActivityAddUpdateToDoListBinding
    private val mTaskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory((application as PomodoroApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAddUpdateToDoListBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        supportActionBar?.title = getString(R.string.add_task)




        var title = mBinding.edTitle.text.toString().trim { it <= ' ' }
        var category: String = ""
        var description = mBinding.edContent.text.toString().trim { it <= ' ' }
        mBinding.spinnerCategory.preferenceName = "category"
        val ts: Long = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())
        val date = Date(ts * 1000L)
        val format: DateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val timeToSave = format.format(date)
        mBinding.spinnerCategory.setOnSpinnerItemSelectedListener<String> {oldIndex, oldItem, newIndex, newText ->
            category = mBinding.spinnerCategory.text.toString()
        }
        if (intent.extras != null){
            title = intent.getStringExtra("title").toString()
            mBinding.edTitle.setText(title)

            category = intent.getStringExtra("category").toString()

            description = intent.getStringExtra("content").toString()
            mBinding.edContent.setText(description)

        }


        mBinding.btSave.setOnClickListener{
            title = mBinding.edTitle.text.toString()
            category = mBinding.spinnerCategory.text.toString()

            description = mBinding.edContent.text.toString()
            when {
                TextUtils.isEmpty(title) -> {
                    Toast.makeText(
                        this,
                        getString(R.string.err_msg_enter_title),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(category) -> {
                    Toast.makeText(
                        this,
                        getString(R.string.err_msg_enter_category),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(description) -> {
                    Toast.makeText(
                        this,
                        getString(R.string.err_msg_enter_description),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val taskDetails = Task(
                        title,
                        timeToSave,
                        category,
                        description,
                        Calendar.getInstance().timeInMillis
                    )
                    mTaskViewModel.insert(taskDetails)
                    Toast.makeText(
                        this,
                        "Siz vazifangizni muvaffaqiyatli qo'shdingiz!",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }
        }
        mBinding.btCancel.setOnClickListener{
            finish()
        }





    }

}