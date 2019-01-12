package com.example.planner.utils

import com.example.planner.R

fun validatePlanTitle(planTitle: String?) = when {
    planTitle.isNullOrBlank() -> R.string.plan_details_error_invalid_title_empty
    planTitle.length < PLAN_TITLE_MIN_LENGTH -> R.string.plan_details_error_invalid_title_too_short
    planTitle.length > PLAN_TITLE_MAX_LENGTH -> R.string.plan_details_error_invalid_title_too_long
    else -> null
}