package com.example.planner.domain.plan

import com.example.planner.database.DaoSession
import com.example.planner.database.PlanEntity
import com.example.planner.database.PlanEntityDao
import java.util.*

class PlanManagerImpl(
    private val daoSession: DaoSession
) : PlanManager {

    override fun getPlans(planSelection: PlanSelection): List<PlanEntity> {
        return daoSession.planEntityDao.queryBuilder()
            .apply {
                when (planSelection) {
                    PlanSelection.DEFAULT -> where(PlanEntityDao.Properties.Deadline.isNull)
                    PlanSelection.DEADLINE -> where(PlanEntityDao.Properties.Deadline.isNotNull)
                    else -> { /* No selection */ }
                }
            }
            .orderAsc(PlanEntityDao.Properties.Position)
            .list()
    }

    override fun getPlan(planId: Long): PlanEntity? = daoSession.planEntityDao.load(planId)

    override fun savePlan(plan: PlanEntity) {
        daoSession.planEntityDao.save(plan)
    }

    override fun deletePlan(planId: Long) {
        daoSession.planEntityDao.deleteByKey(planId)
    }

    override fun duplicatePlan(planId: Long) {
        getPlan(planId)?.let { plan ->
            PlanEntity().let { planEntity ->
                planEntity.title = "${plan.title}*"
                planEntity.description = plan.description
                planEntity.deadline = plan.deadline
                planEntity.position = getNewPosition()
                planEntity.type = plan.type
                planEntity.creationDate = Date()
                savePlan(planEntity)
            }
        }
    }

    override fun decreasePosition(planId: Long) {
        getPlan(planId)?.let { plan ->
            val planPosition = plan.position
            getPreviousPlan(planPosition)?.let { previousPlan ->
                plan.position = previousPlan.position
                savePlan(plan)
                previousPlan.position = planPosition
                savePlan(previousPlan)
            }
        }
    }

    override fun increasePosition(planId: Long) {
        getPlan(planId)?.let { plan ->
            val planPosition = plan.position
            getNextPlan(planPosition)?.let { nextPlan ->
                plan.position = nextPlan.position
                savePlan(plan)
                nextPlan.position = planPosition
                savePlan(nextPlan)
            }
        }
    }

    private fun getPreviousPlan(position: Long) = daoSession.planEntityDao.queryBuilder()
        .where(PlanEntityDao.Properties.Position.lt(position))
        .orderDesc(PlanEntityDao.Properties.Position)
        .list().let { plans ->
            plans.firstOrNull()
        }


    private fun getNextPlan(position: Long) = daoSession.planEntityDao.queryBuilder()
        .where(PlanEntityDao.Properties.Position.gt(position))
        .orderAsc(PlanEntityDao.Properties.Position)
        .list().let { plans ->
            plans.firstOrNull()
        }

    override fun getLastPosition() = daoSession.planEntityDao.queryBuilder()
        .orderDesc(PlanEntityDao.Properties.Position)
        .list().let { plans ->
            plans.firstOrNull()?.position
        }

    override fun getNewPosition() = getLastPosition()?.let { it + 1 } ?: 0

}
