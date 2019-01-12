package com.example.planner.ui.main

interface MainInteractionListener {
    fun showPlan()
    fun showPlanDetails(planId: Long?)
    fun showPlanDetailsDeletion(planId: Long)
    fun hidePlanDetailsDeletion()
}
