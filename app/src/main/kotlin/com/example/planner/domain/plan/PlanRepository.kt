package com.example.planner.domain.plan

import com.example.planner.data.Plan
import io.reactivex.Single

interface PlanRepository {
    fun getPlans(): Single<List<Plan>>
}
