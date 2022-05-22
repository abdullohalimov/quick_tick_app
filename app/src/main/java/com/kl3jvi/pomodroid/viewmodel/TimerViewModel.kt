package com.kl3jvi.pomodroid.viewmodel

import android.app.Application
import android.content.Context
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.kl3jvi.pomodroid.view.activities.MainActivity

class TimerViewModel(application: Application) : AndroidViewModel(application) {
    var COUNTDOWN_LIMIT = 0L
    val ONE_SECOND = 1000L
    lateinit var timer: CountDownTimer
    var timerFinished = MutableLiveData<Boolean>()
    val currentTime = MutableLiveData<Long>()


    private fun setupTime(key: String): Long {
        COUNTDOWN_LIMIT = getTime(key)
        return COUNTDOWN_LIMIT
    }

    private fun timerInit(countDownTimer: Long) {
        timerFinished.value = false
        timer = object : CountDownTimer(setupTime("focus"), ONE_SECOND) {
            override fun onTick(timeLeft: Long) {
                currentTime.value = timeLeft
                COUNTDOWN_LIMIT = timeLeft
            }

            override fun onFinish() {
                val shpref = PreferenceManager.getDefaultSharedPreferences(getApplication())
                val rounds = shpref.getInt("rounds", 0)
                val cround = shpref.getInt("current_round", rounds)
                if (rounds == cround){
                    shpref.edit().putInt("current_rounds", rounds - 1).apply()
                }else
                    shpref.edit().putInt("current_rounds", cround - 1).apply()

                shpref.edit().remove("title").apply()
                shpref.edit().remove("timestamp").apply()
                shpref.edit().remove("category").apply()
                shpref.edit().remove("content").apply()
                shpref.edit().remove("primary").apply()
                shpref.edit().remove("id").apply()
                timerFinished.value = true
            }
        }
    }

    fun start() {
        timerInit(COUNTDOWN_LIMIT)
        timer.start()
    }

    fun stop() {
        timer.cancel()
    }

    private fun getTime(key: String): Long {
        val sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(getApplication())
        return (sharedPreferences.getInt(key, 0) * 60_000).toLong()
    }

    private fun getRounds(key: String): Float{
        val sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(getApplication())
        return (sharedPreferences.getInt(key, 0).toFloat())
    }
}
