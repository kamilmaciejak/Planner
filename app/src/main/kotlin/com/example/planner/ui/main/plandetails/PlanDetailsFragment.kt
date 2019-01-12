package com.example.planner.ui.main.plandetails

import android.arch.lifecycle.Observer
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.example.planner.R
import com.example.planner.databinding.FragmentPlanDetailsBinding
import com.example.planner.ui.main.MainInteractionListener
import com.example.planner.ui.main.plan.PlanViewModel
import com.example.planner.utils.getDescription
import kotlinx.android.synthetic.main.partial_app_bar.*
import kotlinx.android.synthetic.main.partial_date_time.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class PlanDetailsFragment: Fragment() {

    private val planViewModel: PlanViewModel by sharedViewModel()
    private val planDetailsViewModel: PlanDetailsViewModel by viewModel { parametersOf(planId) }
    private var mainInteractionListener: MainInteractionListener? = null
    private val planId by lazy { arguments?.getLong(ARG_PLAN_ID) ?: -1L }
    private val planEventObserver = Observer<Unit> {
        mainInteractionListener?.showPlan()
    }
    private val deletionDialogEventObserver = Observer<Long> { planId ->
        planId?.let {
            mainInteractionListener?.showPlanDetailsDeletion(planId)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return DataBindingUtil.inflate<FragmentPlanDetailsBinding>(inflater, R.layout.fragment_plan_details, container, false).apply {
            planDetailsViewModel = this@PlanDetailsFragment.planDetailsViewModel
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActionBar()
        date_time_time_picker.setIs24HourView(true)
        observeViewModel()
        Timber.d(planViewModel.getDescription())
        Timber.d(planDetailsViewModel.getDescription())
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
        planDetailsViewModel.planEvent.observe(this, planEventObserver)
        planDetailsViewModel.deletionDialogEvent.observe(this, deletionDialogEventObserver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        planDetailsViewModel.planEvent.removeObserver(planEventObserver)
        planDetailsViewModel.deletionDialogEvent.removeObserver(deletionDialogEventObserver)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                return true;
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupActionBar() {
        (activity as AppCompatActivity).apply {
            setSupportActionBar(toolbar)
            supportActionBar?.setTitle(R.string.plan_details_title)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    companion object {
        private const val ARG_PLAN_ID = "ARG_PLAN_ID"
        fun newInstance(planId: Long?) = PlanDetailsFragment().apply {
            planId?.let {
                arguments = Bundle().apply {
                    putLong(ARG_PLAN_ID, planId);
                }
            }
        }
    }
}
