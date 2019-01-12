package com.example.planner.ui.main.plandetails

import android.arch.lifecycle.ViewModel
import android.content.res.Resources
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.example.planner.database.PlanEntity
import com.example.planner.database.PlanType
import com.example.planner.domain.plan.PlanManager
import com.example.planner.ui.DateTimeField
import com.example.planner.ui.SingleLiveEvent
import com.example.planner.ui.ValidationField
import com.example.planner.utils.getDescription
import com.example.planner.utils.validatePlanTitle
import timber.log.Timber
import java.util.*

class PlanDetailsViewModel(
    private val planId : Long,
    private val planManager : PlanManager,
    resources: Resources
) : ViewModel() {
    val title = ValidationField<String>(null, ::validatePlanTitle, resources)
    val description = ObservableField<String>("")
    val deadlineDateTime = DateTimeField()
    val hasDeadlineDateTime = ObservableBoolean()
    var position: Long = 0;
    val important = ObservableBoolean()
    var creationDate: Date? = null
    val planEvent = SingleLiveEvent<Unit>()
    val deletionDialogEvent = SingleLiveEvent<Long>()
    val deletionSuccessEvent = SingleLiveEvent<Unit>()

    init {
        Timber.d(getDescription())
        load()
    }

    private fun load() {
        if (isCreated()) {
            planManager
                .getPlan(planId)?.let { planEntity ->
                    title.set(planEntity.title)
                    description.set(planEntity.description)
                    deadlineDateTime.setDateTime(planEntity.deadline?.time ?: System.currentTimeMillis())
                    hasDeadlineDateTime.set(planEntity.deadline != null)
                    position = planEntity.position
                    important.set(planEntity.type == PlanType.IMPORTANT)
                    creationDate = planEntity.creationDate
                }
        } else {
            deadlineDateTime.setDateTime(System.currentTimeMillis())
            position = planManager.getNewPosition()
        }
    }

    fun save() {
        title.validate()
        if (title.isValid()) {
            PlanEntity().let { planEntity ->
                if (isCreated()) {
                    planEntity.id = planId
                }
                planEntity.title = title.get()
                planEntity.description = description.get()
                planEntity.deadline = if (hasDeadlineDateTime.get()) deadlineDateTime.getTime() else null
                planEntity.position = position
                planEntity.type = if (important.get()) PlanType.IMPORTANT else PlanType.DEFAULT
                planEntity.creationDate = creationDate ?: Date()
                planManager.savePlan(planEntity)
            }
            planEvent.call()
        }
    }

    fun decreasePosition() {
        planManager
            .decreasePosition(planId)
        planEvent.call()
    }

    fun increasePosition() {
        planManager
            .increasePosition(planId)
        planEvent.call()
    }

    fun duplicate() {
        planManager
            .duplicatePlan(planId)
        planEvent.call()
    }

    fun showDeletionDialog() {
        deletionDialogEvent.value = planId
    }

    fun delete() {
        planManager
            .deletePlan(planId)
        deletionSuccessEvent.call()
    }

    fun isCreated() = planId > 0
}
