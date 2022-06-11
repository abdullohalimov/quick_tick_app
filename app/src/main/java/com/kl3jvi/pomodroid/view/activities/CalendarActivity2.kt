package com.kl3jvi.pomodroid.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.EventDay
import com.kl3jvi.pomodroid.R
import com.kl3jvi.pomodroid.application.PomodoroApplication
import com.kl3jvi.pomodroid.model.entities.Task
import com.kl3jvi.pomodroid.viewmodel.TaskViewModel
import com.kl3jvi.pomodroid.viewmodel.TaskViewModelFactory
import com.mogalabs.tagnotes.data.Note
import com.mogalabs.tagnotes.viewmodels.NoteViewModel
import java.util.*

class CalendarActivity2 : AppCompatActivity() {

    private val mTaskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory((application as PomodoroApplication).repository)
    }

    private lateinit var noteViewModel: NoteViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar2)

        val events: MutableList<EventDay> = ArrayList()
        val days: MutableList<Calendar> = ArrayList()

        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        val alltask = mTaskViewModel.allTaskList
        var l = 1
        while (l < alltask.size){
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = alltask[l].date
            events.add(EventDay(calendar, R.drawable.sample_icon2))
            l++
        }

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        val allnotes = noteViewModel.getAllNotes()
        var i = 1
        while (i < allnotes.size){
            val calendar2 = Calendar.getInstance()
            calendar2.timeInMillis = allnotes[i].date
            days.add(calendar2)
            i++
        }

        calendarView.selectedDates = days
        calendarView.setEvents(events)

    }
}