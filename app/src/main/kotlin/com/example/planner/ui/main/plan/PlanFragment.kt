package com.example.planner.ui.main.plan

import android.arch.lifecycle.Observer
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.example.planner.R
import com.example.planner.database.PlanEntity
import com.example.planner.databinding.FragmentPlanBinding
import com.example.planner.ui.main.MainInteractionListener
import com.example.planner.utils.getDescription
import kotlinx.android.synthetic.main.fragment_plan.*
import kotlinx.android.synthetic.main.partial_app_bar.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class PlanFragment : Fragment(), OnPlanClickListener {

    private val planViewModel: PlanViewModel by sharedViewModel()
    private var mainInteractionListener: MainInteractionListener? = null
    private val planListEventObserver = Observer<List<PlanEntity>> { planList ->
        (plan_recycler.adapter as PlanListAdapter).submitList(planList)
    }
    private val planDetailsEventObserver = Observer<Long?> { planId ->
        mainInteractionListener?.showPlanDetails(planId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return DataBindingUtil.inflate<FragmentPlanBinding>(inflater, R.layout.fragment_plan, container, false).apply {
            planViewModel = this@PlanFragment.planViewModel
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActionBar()
        setupPlanRecycler()
        observeViewModel()
        planViewModel.load()
        Timber.d(planViewModel.getDescription())
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
        planViewModel.planListEvent.observe(this, planListEventObserver)
        planViewModel.planDetailsEvent.observe(this, planDetailsEventObserver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        planViewModel.planListEvent.removeObserver(planListEventObserver)
        planViewModel.planDetailsEvent.removeObserver(planDetailsEventObserver)
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
            supportActionBar?.setTitle(R.string.plan_name_title)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setupPlanRecycler() {
        val planListAdapter = PlanListAdapter(this)
        plan_recycler.isNestedScrollingEnabled = false
        plan_recycler.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        plan_recycler.adapter = planListAdapter
    }

    override fun onPlanClick(planId: Long) {
        mainInteractionListener?.showPlanDetails(planId)
    }

    companion object {
        fun newInstance() = PlanFragment()
    }

}
