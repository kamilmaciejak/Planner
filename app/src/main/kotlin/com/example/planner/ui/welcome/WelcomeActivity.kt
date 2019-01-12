package com.example.planner.ui.welcome

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.planner.R
import com.example.planner.databinding.ActivityWelcomeBinding
import com.example.planner.ui.main.MainActivity
import com.example.planner.ui.settings.SettingsActivity
import com.example.planner.utils.getDescription
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class WelcomeActivity : AppCompatActivity() {

    private val welcomeViewModel: WelcomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindViewModel()
        observeEvents()
        Timber.d(welcomeViewModel.getDescription())
    }

    private fun bindViewModel() {
        DataBindingUtil.setContentView<ActivityWelcomeBinding>(this, R.layout.activity_welcome).apply {
            welcomeViewModel = this@WelcomeActivity.welcomeViewModel
        }
    }

    private fun observeEvents() {
        welcomeViewModel.plannerEvent.observe(this, Observer {
            MainActivity.start(this)
        })
        welcomeViewModel.settingsEvent.observe(this, Observer {
            SettingsActivity.start(this)
        })
    }
}
