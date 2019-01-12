package com.example.planner.ui

import android.databinding.BindingAdapter
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.planner.R
import com.example.planner.database.PlanType
import com.example.planner.domain.plan.PlanSelection
import com.example.planner.utils.getDateFormat
import com.example.planner.utils.getDateMultiLineFormat
import com.example.planner.utils.getDaysToDate
import java.util.*

@BindingAdapter("android:visibility")
fun setVisibility(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("android:invisibility")
fun setInvisibility(view: View, invisible: Boolean) {
    view.visibility = if (invisible) View.INVISIBLE else View.VISIBLE
}

@BindingAdapter("creationDate")
fun setCreationDate(view: TextView, date: Date?) {
    view.text = String.format(view.context.getString(R.string.creation_date), date?.let { getDateFormat().format(it) } ?: "")
}

@BindingAdapter("date")
fun setDate(view: TextView, date: Date) {
    view.text = getDateMultiLineFormat().format(date)
}

@BindingAdapter("deadlineDays")
fun setDeadlineDays(view: TextView, date: Date) {
    val daysDiff = getDaysToDate(date)
    when {
        daysDiff < 0L -> view.text = "?"
        daysDiff == 0L -> view.text = "!"
        else -> view.text = daysDiff.toString()
    }
}

@BindingAdapter("planTypeColor")
fun setPlanTypeColor(view: ViewGroup, planType: PlanType) {
    if (PlanType.DEFAULT == planType) {
        view.setBackgroundColor(Color.TRANSPARENT)
    } else {
        view.setBackgroundColor(ContextCompat.getColor(view.context, android.R.color.holo_red_light))
    }
}

@BindingAdapter(
    "planSelection",
    "planSelectionValue"
)
fun setPlanSelectionColor(view: TextView,
                          planSelection: PlanSelection,
                          planSelectionValue: PlanSelection
) {
    if (planSelection == planSelectionValue) {
        view.setBackgroundColor(view.textColors.defaultColor)
        view.background.alpha = 100
    } else {
        view.setBackgroundColor(Color.TRANSPARENT)
        view.background.alpha = 255
    }
}
