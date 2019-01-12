package com.example.planner.receiver

import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.planner.domain.plan.PreferencesManager
import com.example.planner.domain.plan.PreferencesManager.Companion.PREF_ALARM_TIME_MILLIS
import com.example.planner.utils.getAlarmManager
import com.example.planner.utils.getAlarmPendingIntent
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class DeviceBootReceiver : BroadcastReceiver(), KoinComponent {

    private val preferencesManager: PreferencesManager by inject()

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            setAlarm(context)
        }
    }

    private fun setAlarm(context: Context) {
        if (preferencesManager.contains(PREF_ALARM_TIME_MILLIS)) {
            getAlarmManager(context).set(
                AlarmManager.RTC_WAKEUP,
                preferencesManager.alarmTimeMillis,
                getAlarmPendingIntent(context)
            )
        }
    }
}
