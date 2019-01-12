package com.example.planner.ui.main

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.planner.R
import com.example.planner.databinding.ActivityMainBinding
import com.example.planner.ui.main.plan.PlanFragment
import com.example.planner.ui.main.plandetails.PlanDetailsDeletionDialog
import com.example.planner.ui.main.plandetails.PlanDetailsFragment
import com.example.planner.utils.getDescription
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber


class MainActivity : AppCompatActivity(), MainInteractionListener {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindViewModel()

        if (savedInstanceState == null) {
            showPlan()
        }
        Timber.d(mainViewModel.getDescription())
    }

    private fun bindViewModel() {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
            mainViewModel = this@MainActivity.mainViewModel
        }
    }

    // ok like here
//    override fun onSupportNavigateUp(): Boolean {
//        super.onSupportNavigateUp()
//        onBackPressed()
//        return true
//    }

    override fun showPlan() {
        if (supportFragmentManager.fragments.isEmpty()) {
            PlanFragment.newInstance().apply {
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.main_layout, this)
                    .commit()
            }
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    override fun showPlanDetails(planId: Long?) {
        PlanDetailsFragment.newInstance(planId).apply {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_layout, this)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun showPlanDetailsDeletion(planId: Long) {
        PlanDetailsDeletionDialog.newInstance(planId).apply {
            show(supportFragmentManager, PlanDetailsDeletionDialog.TAG)
        }
    }

    override fun hidePlanDetailsDeletion() {
        supportFragmentManager.findFragmentByTag(PlanDetailsDeletionDialog.TAG)?.let { fragment ->
            (fragment as PlanDetailsDeletionDialog).dismiss()
        }
    }

    companion object {
        fun start(context: Context) = context.startActivity(Intent(context, MainActivity::class.java))
    }
}
