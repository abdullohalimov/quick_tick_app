package com.kl3jvi.pomodroid.view.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import com.kl3jvi.pomodroid.application.PomodoroApplication
import com.kl3jvi.pomodroid.databinding.FragmentTimerBinding
import com.kl3jvi.pomodroid.model.entities.Task
import com.kl3jvi.pomodroid.view.adapters.TaskAdapter
import com.kl3jvi.pomodroid.viewmodel.TaskViewModel
import com.kl3jvi.pomodroid.viewmodel.TaskViewModelFactory
import com.kl3jvi.pomodroid.viewmodel.TimerViewModel
import com.maxkeppeler.sheets.info.InfoSheet
import me.zhanghai.android.materialplaypausedrawable.MaterialPlayPauseDrawable


class TimerFragment : Fragment() {

    private var mBinding: FragmentTimerBinding? = null
    private var timeWhenStopped: Long = 0
    private lateinit var mTimerViewModel: TimerViewModel



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("onCreateView", "initialised")
        mBinding = FragmentTimerBinding.inflate(inflater, container, false)
        val root: View = mBinding!!.root
        val shpref = PreferenceManager.getDefaultSharedPreferences(root.context)
        mBinding!!.recyclerView2.layoutManager = GridLayoutManager(requireActivity(), 1)
        val mTaskAdapter = TaskAdapter(this@TimerFragment)
        mBinding!!.recyclerView2.adapter = mTaskAdapter
        val data: ArrayList<Task> = ArrayList()
        data.add(Task(
            shpref.getString("title", "Fokusga vazifa qo'shilmagan!").toString(),
            shpref.getString("timestamp", "").toString(),
            shpref.getString("category", "").toString(),
            shpref.getString("content", "Ma'lum bir vazifada diqqatingizni jamlash uchun uni fokusga qo'shing").toString(),
            shpref.getBoolean("primary", true),
            shpref.getInt("id", 0)))
        mTaskAdapter.tasksList(data)
        return root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("onViewCreated", "initialised")
        // To save state after going to another fragment i initialize the view model by activity
        // so it gets destroyed when the activity dies
        mTimerViewModel = ViewModelProvider(activity as ViewModelStoreOwner).get(TimerViewModel::class.java)
        val playbutton = mBinding!!.playPause
        val round_progressbar = mBinding!!.progressRounds
        val time_progressbar = mBinding!!.circularProgressBar

        val rounds = getRounds("rounds", 8)
        val current_rounds = getRounds("current_rounds", rounds.toInt())
        round_progressbar.progressMax = rounds
        round_progressbar.progress = current_rounds
        mBinding!!.roundsText.text = "Aylana: ${current_rounds.toInt()}"

        playbutton.setOnClickListener {
            val newState =
                if (playbutton.state === PLAY)
                    PAUSE else PLAY
            playbutton.state = newState

            when (newState) {
                PLAY -> {
                    mTimerViewModel.stop()
                }
                PAUSE -> {

                    mTimerViewModel.start()
                }
                else -> {
                    Log.e("Error", "Something went wrong with play/pause")
                }
            }
        }

        time_progressbar.progressMax = getTime("focus").toFloat()
        mTimerViewModel.currentTime.observe(viewLifecycleOwner) { time ->
            mBinding!!.time.text = String.format(
                "%02d:%02d",
                java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(time),
                java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(time) -
                        java.util.concurrent.TimeUnit.MINUTES.toSeconds(
                            java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(
                                time
                            )
                        )
            )
            mBinding!!.circularProgressBar.setProgressWithAnimation(time.toFloat(), 1000)
        }

        mTimerViewModel.timerFinished.observe(viewLifecycleOwner) { paused ->

            if (paused) {
                mBinding!!.playPause.state = PLAY
            } else {
                mBinding!!.playPause.state = PAUSE
            }
        }


    }

    private fun getTime(key: String): Long {
        val sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(requireActivity())
        return (sharedPreferences.getInt(key, 0) * 60_000).toLong()
    }
    private fun getRounds(key: String, default: Int): Float{
        val sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(requireActivity())
        return (sharedPreferences.getInt(key, default).toFloat())
    }



    companion object {
        const val CHANNEL_ID: String = "1000"
        val PLAY = MaterialPlayPauseDrawable.State.Play
        val PAUSE = MaterialPlayPauseDrawable.State.Pause

    }


}


