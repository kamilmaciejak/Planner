package com.example.planner.domain.plan

import android.content.SharedPreferences

class PreferencesManager(
    private val preferences: SharedPreferences
) {

    var alarmTimeMillis: Long
        get() = getLong(PREF_ALARM_TIME_MILLIS, System.currentTimeMillis())
        set(value) = put(PREF_ALARM_TIME_MILLIS, value)

    var planSelection: PlanSelection
        get() = getPlanSelection(PREF_PLAN_SELECTION)
        set(value) = put(PREF_PLAN_SELECTION, value)

    private fun getBoolean(key: String) = preferences.getBoolean(key, false)

    private fun getInt(key: String, defValue: Int = 0) = preferences.getInt(key, defValue)

    private fun getLong(key: String, defValue: Long = 0) = preferences.getLong(key, defValue)

    private fun getString(key: String, defValue: String? = null) = preferences.getString(key, defValue)

    private fun getPlanSelection(key: String, defValue: PlanSelection = PlanSelection.ALL) =
        preferences.getString(key, null)?.let { PlanSelection.valueOf(it) } ?: defValue

    private fun <T> put(key: String, value: T) {
        preferences.edit().apply {
            when (value) {
                is Boolean -> putBoolean(key, value)
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is String -> putString(key, value)
                is PlanSelection -> putString(key, value.toString())
                else -> throw UnsupportedOperationException("Not implemented")
            }
            apply()
        }
    }

    fun contains(key: String) = preferences.contains(key)

    fun remove(key: String) {
        preferences.edit().apply {
            remove(key)
            apply()
        }
    }

    companion object {
        const val PREF_ALARM_TIME_MILLIS = "PREF_ALARM_TIME_MILLIS"
        const val PREF_PLAN_SELECTION= "PREF_PLAN_SELECTION"
    }
}
