package com.example.planner.ui.main.plan

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.example.planner.database.PlanEntity
import com.example.planner.domain.plan.PlanManager
import com.example.planner.domain.plan.PlanRepository
import com.example.planner.domain.plan.PlanSelection
import com.example.planner.domain.plan.PreferencesManager
import com.example.planner.ui.SingleLiveEvent
import com.example.planner.utils.applyIoScheduler
import com.example.planner.utils.getDescription
import io.reactivex.disposables.Disposable
import timber.log.Timber

class PlanViewModel(
    private val planManager: PlanManager,
    private val planRepository : PlanRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {
    val planSelection = ObservableField<PlanSelection>(preferencesManager.planSelection)
    private val planList = mutableListOf<PlanEntity>()
    val planListEvent = SingleLiveEvent<List<PlanEntity>>()
    val planDetailsEvent = SingleLiveEvent<Long?>()
    private var disposable: Disposable? = null

    init {
        Timber.d(getDescription())
    }

    fun load() {
        load(planSelection.get() as PlanSelection)
    }

    fun load(planSelection: PlanSelection) {
        updatePlanSelection(planSelection)
        planManager
            .getPlans(planSelection).apply {
                planList.addAll(this)
                planListEvent.value = this
            }
        // loadFromRepository()
    }

    private fun updatePlanSelection(planSelection: PlanSelection) {
        if (this.planSelection.get() != planSelection) {
            preferencesManager.planSelection = planSelection
            this.planSelection.set(planSelection)
        }
    }

    private fun loadFromRepository() {
        disposable?.dispose()
        disposable = planRepository
            .getPlans()
            .applyIoScheduler()
            .doOnSubscribe {
                Timber.d("getPlans()")
            }
            .subscribe({ plans ->
                Timber.d(plans.toString())
            }, { error ->
                Timber.e(error)
            })
    }

    fun addPlanDetails() {
        planDetailsEvent.call()
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}
