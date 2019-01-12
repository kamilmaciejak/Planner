package com.example.planner.ui

import android.databinding.ObservableInt
import com.example.planner.utils.*
import java.util.*

class DateTimeField {
    val year = ObservableInt()
    val month = ObservableInt()
    val day = ObservableInt()
    val hour = ObservableInt()
    val minute = ObservableInt()

    fun setDateTime(timeInMillis: Long) {
        getCalendar(timeInMillis).let {
            year.set(getYear(it))
            month.set(getMonth(it))
            day.set(getDay(it))
            hour.set(getHour(it))
            minute.set(getMinute(it))
        }
    }

    fun getTime(): Date = getCalendar(year.get(), month.get(), day.get(), hour.get(), minute.get()).time

    fun getTimeInMillis() = getCalendar(year.get(), month.get(), day.get(), hour.get(), minute.get()).timeInMillis
}
