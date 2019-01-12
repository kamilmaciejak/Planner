package com.example.planner.domain.plan

import com.example.planner.data.Plan
import io.reactivex.Single
import java.util.*
import java.util.concurrent.TimeUnit

class PlanRepositoryImpl : PlanRepository {

    override fun getPlans(): Single<List<Plan>> = Single.just(Arrays.asList(
        Plan(1, "Title 1", "Description 1"),
        Plan(2, "Title 2", "Description 2")))
        .delay(7, TimeUnit.SECONDS)

}
