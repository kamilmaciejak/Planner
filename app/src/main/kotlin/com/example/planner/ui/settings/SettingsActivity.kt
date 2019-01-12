package com.example.planner.ui.settings

import android.app.AlarmManager
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.example.planner.R
import com.example.planner.databinding.ActivitySettingsBinding
import com.example.planner.utils.getAlarmManager
import com.example.planner.utils.getAlarmPendingIntent
import com.example.planner.utils.getDescription
import kotlinx.android.synthetic.main.partial_date_time.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class SettingsActivity : AppCompatActivity() {

    private val settingsViewModel: SettingsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        bindViewModel()
        date_time_time_picker.setIs24HourView(true)
        observeEvents()
        Timber.d(settingsViewModel.getDescription())
    }

    private fun bindViewModel() {
        DataBindingUtil.setContentView<ActivitySettingsBinding>(this, R.layout.activity_settings).apply {
            settingsViewModel = this@SettingsActivity.settingsViewModel
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true;
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun observeEvents() {
        settingsViewModel.alarmEvent.observe(this, Observer { active ->
            active?.let {
                if (active) {
                    setAlarm()
                    Toast.makeText(application, R.string.settings_alarm_set, Toast.LENGTH_LONG).show()

                } else {
                    cancelAlarm()
                    Toast.makeText(application, R.string.settings_alarm_canceled, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun setAlarm() {
        getAlarmManager(this).set(
            AlarmManager.RTC_WAKEUP,
            settingsViewModel.getAlarmTimeMillis(),
            getAlarmPendingIntent(this)
        )
    }

    private fun cancelAlarm() {
        getAlarmManager(this).cancel(getAlarmPendingIntent(this))
    }

    companion object {
        fun start(context: Context) = context.startActivity(Intent(context, SettingsActivity::class.java))
    }
}
