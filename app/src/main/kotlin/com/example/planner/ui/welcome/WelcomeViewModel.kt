package com.example.planner.ui.welcome

import android.arch.lifecycle.ViewModel
import com.example.planner.ui.SingleLiveEvent

class WelcomeViewModel : ViewModel() {
    val plannerEvent = SingleLiveEvent<Unit>()
    val settingsEvent = SingleLiveEvent<Unit>()

    fun showPlanner() {
        plannerEvent.call()
    }

    fun showSettings() {
        settingsEvent.call()
    }

}