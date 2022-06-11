package com.kl3jvi.pomodroid.view.adapters

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.kl3jvi.pomodroid.R
import com.kl3jvi.pomodroid.databinding.ItemTaskBinding
import com.kl3jvi.pomodroid.model.entities.Task
import com.kl3jvi.pomodroid.utils.Constants
import com.kl3jvi.pomodroid.view.activities.AddUpdateToDoList
import com.kl3jvi.pomodroid.view.fragments.TaskListFragment
import com.maxkeppeler.sheets.options.Option
import com.maxkeppeler.sheets.options.OptionsSheet

class TaskAdapter(private val fragment: Fragment) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    private var tasks: List<Task> = listOf()

    class ViewHolder(view: ItemTaskBinding) : RecyclerView.ViewHolder(view.root) {
        val title = view.tvTitle
        val time = view.tvTime
        val chip = view.chipCategory
        val content = view.tvContent
        val more = view.ibMore
        val focus = view.focus2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemTaskBinding =
            ItemTaskBinding.inflate(
                LayoutInflater.from((fragment.context)), parent, false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks[position]
        val shpref = PreferenceManager.getDefaultSharedPreferences(fragment.requireContext())
        val focus3 = shpref.getInt("focus", 0)
        if (task.id == focus3){
            holder.focus.text = "Fokusda!"
        }

        holder.title.text = task.title
        holder.time.text = task.timeStamp
        holder.content.text = task.content
        holder.chip.text = task.category
        holder.chip.setTextColor(Color.WHITE)


        holder.more.setOnClickListener {
            OptionsSheet().show(fragment.requireContext()) {
                title("Optsiyalar")
                with(
                    Option(R.drawable.ic_edit, "O'zgartirish"),
                    Option(R.drawable.ic_delete, "O'chirish"),
                    Option(R.drawable.ic_add, "Fokusga qo'shish"),
                    Option(R.drawable.ic_config, "Fokusdan olish"),
                )
                onPositive { index: Int, _: Option ->
                    // Handle selected option
                    when (index) {
                        0 -> {
                            val intent =
                                Intent(fragment.requireActivity(), AddUpdateToDoList::class.java)
                            intent.putExtra("title", task.title)
                            intent.putExtra("content", task.content)
                            intent.putExtra("category", task.category)
                            if (fragment is TaskListFragment){
                                fragment.deleteWithoutAlert(task)
                            }
                            fragment.requireActivity().startActivity(intent)

                        }
                        1 -> {
                            if (fragment is TaskListFragment) {
                                fragment.deleteTask(task)
                            }
                        }

                        2 -> {
                            shpref.edit().putString("title", task.title).apply()
                            shpref.edit().putString("timestamp", task.timeStamp).apply()
                            shpref.edit().putString("category", task.category).apply()
                            shpref.edit().putString("content", task.content).apply()
                            shpref.edit().putBoolean("primary", task.primaryTask).apply()
                            shpref.edit().putInt("id", task.id).apply()
                            shpref.edit().putInt("focus", task.id).apply()
                            holder.focus.text = "Fokusda!"
                            Toast.makeText(context, "Vazifa fokusga qo'shildi", Toast.LENGTH_SHORT).show( )
                        }

                        3 -> {
                            shpref.edit().putString("title", "Fokusga vazifa qo'shilmagan!").apply()
                            shpref.edit().putString("timestamp", "").apply()
                            shpref.edit().putString("category", "").apply()
                            shpref.edit().putString("content", "Ma'lum bir vazifada diqqatingizni jamlash uchun uni fokusga qo'shing").apply()
                            shpref.edit().putBoolean("primary", false).apply()
                            shpref.edit().putInt("id", 0).apply()
                            shpref.edit().putInt("focus", 1).apply()
                            holder.focus.text = "Fokusda emas"

                            notifyItemChanged(0)
                        }
                    }
                }
            }


        }

    }


    override fun getItemCount(): Int {
        return tasks.size
    }

    fun tasksList(list: List<Task>) {
        tasks = list
        notifyDataSetChanged()
    }
}