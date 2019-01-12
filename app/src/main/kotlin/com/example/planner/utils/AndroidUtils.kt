package com.example.planner.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.planner.PlannerApplication
import com.example.planner.domain.plan.PlanManager
import com.example.planner.domain.plan.PlanManagerImpl
import com.example.planner.domain.plan.PreferencesManager
import com.example.planner.receiver.AlarmReceiver

fun getDefaultSharedPreferences(context: Context): SharedPreferences =
    PreferenceManager.getDefaultSharedPreferences(context)

fun getPreferencesManager(context: Context) =
    PreferencesManager(getDefaultSharedPreferences(context))

fun getDaoSession(context: Context) =
    (context.applicationContext as PlannerApplication).daoSession

fun getPlanManager(context: Context): PlanManager =
    PlanManagerImpl(getDaoSession(context))

fun getAlarmManager(context: Context) =
    context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

fun getAlarmPendingIntent(context: Context): PendingIntent =
    Intent(context, AlarmReceiver::class.java).let { intent ->
        PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
    }
