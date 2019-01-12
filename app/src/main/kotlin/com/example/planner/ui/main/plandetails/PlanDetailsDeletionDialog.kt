package com.example.planner.ui.main.plandetails

import android.app.AlertDialog
import android.app.Dialog
import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import com.example.planner.R
import com.example.planner.ui.main.MainInteractionListener
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PlanDetailsDeletionDialog : DialogFragment() {

    private val planId by lazy { arguments?.getLong(ARGUMENT_PLAN_ID) }
    private val planDetailsViewModel: PlanDetailsViewModel by viewModel { parametersOf(planId) }
    private var mainInteractionListener: MainInteractionListener? = null
    private val deletionSuccessEventObserver = Observer<Unit> {
        mainInteractionListener?.apply {
            hidePlanDetailsDeletion()
            showPlan()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        isCancelable = false
        observeViewModel()
        return AlertDialog.Builder(activity)
            .setTitle(R.string.plan_details_deletion_title)
            .setPositiveButton(R.string.ok) { _, _ ->
                planDetailsViewModel.delete()
            }
            .setNegativeButton(R.string.cancel) { _, _ ->
                mainInteractionListener?.hidePlanDetailsDeletion()
            }
            .create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainInteractionListener) {
            mainInteractionListener = context
        } else {
            throw RuntimeException("$context must implement MainInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mainInteractionListener = null
    }

    private fun observeViewModel() {
        planDetailsViewModel.deletionSuccessEvent.observe(this, deletionSuccessEventObserver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        planDetailsViewModel.deletionSuccessEvent.removeObserver(deletionSuccessEventObserver)
    }

    companion object {
        const val TAG = "PlanDetailsDeletionDialog"
        private const val ARGUMENT_PLAN_ID = "ARGUMENT_PLAN_ID"
        fun newInstance(planId: Long) = PlanDetailsDeletionDialog().apply {
            arguments = Bundle().apply {
                putLong(ARGUMENT_PLAN_ID, planId)
            }
        }
    }
}
