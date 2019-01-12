package com.example.planner

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.example.planner.database.DaoMaster
import com.example.planner.database.DaoSession
import com.example.planner.database.DatabaseHelper
import com.example.planner.di.appModule
import com.example.planner.utils.DB_NAME
import com.example.planner.utils.NOTIFICATION_CHANNEL_DESCRIPTION
import com.example.planner.utils.NOTIFICATION_CHANNEL_ID
import com.example.planner.utils.NOTIFICATION_CHANNEL_NAME
import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class PlannerApplication : Application() {

    lateinit var daoSession: DaoSession

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return;
            }
            LeakCanary.install(this)
            Timber.plant(Timber.DebugTree())
            Stetho.initializeWithDefaults(this)
        }
        setupDatabase()
        setupDI()
        setupNotificationChannel()
    }

    private fun setupDatabase() {
        val databaseHelper = DatabaseHelper(this, DB_NAME)
        val database = databaseHelper.writableDatabase
        daoSession = DaoMaster(database).newSession()
    }

    private fun setupDI() {
        startKoin(this, listOf(appModule))
    }

    private fun setupNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = NOTIFICATION_CHANNEL_DESCRIPTION
            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).apply {
                createNotificationChannel(channel)
            }
        }
    }
}
