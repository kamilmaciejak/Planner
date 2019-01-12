package com.example.planner.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun getCalendar(year: Int,
                month: Int,
                day: Int,
                hour: Int,
                minute: Int): Calendar = Calendar.getInstance().apply {
    set(Calendar.YEAR, year)
    set(Calendar.MONTH, month)
    set(Calendar.DAY_OF_MONTH, day)
    set(Calendar.HOUR_OF_DAY, hour)
    set(Calendar.MINUTE, minute)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
}

fun getCalendar(timeInMillis: Long): Calendar = Calendar.getInstance().apply {
    this.timeInMillis = timeInMillis
}

fun getCalendarWithoutTime(timeInMillis: Long) = getCalendar(timeInMillis).apply {
    set(Calendar.HOUR_OF_DAY, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
}

fun getYear(calendar: Calendar) = calendar.get(Calendar.YEAR)
fun getMonth(calendar: Calendar) = calendar.get(Calendar.MONTH)
fun getDay(calendar: Calendar) = calendar.get(Calendar.DAY_OF_MONTH)
fun getHour(calendar: Calendar) = calendar.get(Calendar.HOUR_OF_DAY)
fun getMinute(calendar: Calendar) = calendar.get(Calendar.MINUTE)

fun getDaysToDate(date: Date) = TimeUnit.MILLISECONDS.toDays(getCalendarWithoutTime(date.time).timeInMillis
        - getCalendarWithoutTime(System.currentTimeMillis()).timeInMillis)

fun getDateFormat() = SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault())
fun getDateMultiLineFormat() = SimpleDateFormat(DATE_TIME_MULTI_LINE_FORMAT, Locale.getDefault())
