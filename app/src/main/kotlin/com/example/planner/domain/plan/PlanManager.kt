package com.example.planner.domain.plan

import com.example.planner.database.PlanEntity

interface PlanManager {
    fun getPlans(planSelection: PlanSelection): List<PlanEntity>
    fun getPlan(planId: Long): PlanEntity?
    fun savePlan(plan: PlanEntity)
    fun deletePlan(planId: Long)
    fun duplicatePlan(planId: Long)
    fun decreasePosition(planId: Long)
    fun increasePosition(planId: Long)
    fun getLastPosition(): Long?
    fun getNewPosition(): Long
}
