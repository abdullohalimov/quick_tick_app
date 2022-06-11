package com.kl3jvi.pomodroid.view.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kl3jvi.pomodroid.R
import com.kl3jvi.pomodroid.databinding.ActivityAddNoteBinding
import com.kl3jvi.pomodroid.databinding.ActivityMain2Binding
import java.util.*


class AddEditNoteActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ID = "com.kl3jvi.pomodroid.view.activities.EXTRA_ID"
        const val EXTRA_TITLE = "com.kl3jvi.pomodroid.view.activities.EXTRA_TITLE"
        const val EXTRA_DESCRIPTION = "com.kl3jvi.pomodroid.view.activities.EXTRA_DESCRIPTION"
        const val EXTRA_PRIORITY = "com.kl3jvi.pomodroid.view.activities.EXTRA_PRIORITY"
        const val EXTRA_DATE = "com.kl3jvi.pomodroid.view.activities.EXTRA_DATE"
    }
    private lateinit var binding: ActivityAddNoteBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val number_picker_priority = binding.numberPickerPriority
        val edit_text_description = binding.editTextDescription
        val edit_text_title = binding.editTextTitle
        number_picker_priority.minValue = 1
        number_picker_priority.maxValue = 10


        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

        if (intent.hasExtra(EXTRA_ID)) {
            title = "Edit Note"
            edit_text_title.setText(intent.getStringExtra(EXTRA_TITLE))
            edit_text_description.setText(intent.getStringExtra(EXTRA_DESCRIPTION))
            number_picker_priority.value = intent.getIntExtra(EXTRA_PRIORITY, 1)
        } else {
            title = "Add Note"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item?.itemId) {
            R.id.save_note -> {
                saveNote()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveNote() {
        val number_picker_priority = binding.numberPickerPriority
        val edit_text_description = binding.editTextDescription
        val edit_text_title = binding.editTextTitle
//        if (edit_text_title.text.toString().isBlank() || edit_text_description.text.toString().trim().isBlank()) {
//            Toast.makeText(this, "Can not insert empty note!", Toast.LENGTH_SHORT).show()
//            return
//        }

        val data = Intent().apply {
            putExtra(EXTRA_TITLE, edit_text_title.text.toString())
            putExtra(EXTRA_DESCRIPTION, edit_text_description.text.toString())
            putExtra(EXTRA_PRIORITY, number_picker_priority.value)
            putExtra(EXTRA_DATE, Calendar.getInstance().timeInMillis)
            if (intent.getIntExtra(EXTRA_ID, -1) != -1) {
                putExtra(EXTRA_ID, intent.getIntExtra(EXTRA_ID, -1))
            }
        }

        setResult(Activity.RESULT_OK, data)
        finish()
    }
}