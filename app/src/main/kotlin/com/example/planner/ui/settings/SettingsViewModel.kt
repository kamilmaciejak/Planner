package com.example.planner.ui.settings

import android.arch.lifecycle.ViewModel
import com.example.planner.domain.plan.PreferencesManager
import com.example.planner.ui.DateTimeField
import com.example.planner.ui.SingleLiveEvent

class SettingsViewModel(
    private val preferencesManager: PreferencesManager
) : ViewModel() {
    val alarmDateTime = DateTimeField()
    val alarmEvent = SingleLiveEvent<Boolean>()

    init {
        load()
    }

    private fun load() {
        alarmDateTime.setDateTime(getAlarmTimeMillis())
    }

    fun getAlarmTimeMillis() = preferencesManager.alarmTimeMillis

    fun save() {
        preferencesManager.alarmTimeMillis = alarmDateTime.getTimeInMillis()
        alarmEvent.value = true
    }

    fun delete() {
        preferencesManager.remove(PreferencesManager.PREF_ALARM_TIME_MILLIS)
        load()
        alarmEvent.value = false
    }
}
