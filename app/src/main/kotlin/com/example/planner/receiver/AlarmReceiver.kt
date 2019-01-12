package com.example.planner.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import com.example.planner.R
import com.example.planner.database.PlanEntity
import com.example.planner.domain.plan.PlanManager
import com.example.planner.domain.plan.PlanSelection
import com.example.planner.domain.plan.PreferencesManager
import com.example.planner.domain.plan.PreferencesManager.Companion.PREF_ALARM_TIME_MILLIS
import com.example.planner.utils.NOTIFICATION_CHANNEL_ID
import com.example.planner.utils.NOTIFICATION_ID
import com.example.planner.utils.getAlarmManager
import com.example.planner.utils.getAlarmPendingIntent
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class AlarmReceiver : BroadcastReceiver(), KoinComponent {

    private val preferencesManager: PreferencesManager by inject()
    private val planManager: PlanManager by inject()

    override fun onReceive(context: Context, intent: Intent) {
        showPlans(context)
    }

    private fun showPlans(context: Context) {
        planManager.getPlans(PlanSelection.DEADLINE).let { plans ->
            if (plans.isNotEmpty()) {
                getAlarmText(plans).let { alarmText ->
                    if (alarmText.isNotEmpty()) {
                        showNotification(context, alarmText)
//                        Toast.makeText(context, alarmText, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        cancelAlarm(context)
    }

    private fun getAlarmText(plans: List<PlanEntity>): String {
        var text = ""
        plans.filter { hasDeadline(it) }.forEach { plan ->
            text += "${plan.title}\n"
        }
        return text
    }

    private fun hasDeadline(planEntity: PlanEntity) = planEntity.deadline?.let { it.time > System.currentTimeMillis() } ?: false

    private fun showNotification(context: Context, text: String) {
        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText(text)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(text))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context).apply {
            notify(NOTIFICATION_ID, notification)
        }
    }

    private fun cancelAlarm(context: Context) {
        preferencesManager.remove(PREF_ALARM_TIME_MILLIS)
        getAlarmManager(context).cancel(getAlarmPendingIntent(context))
    }
}
