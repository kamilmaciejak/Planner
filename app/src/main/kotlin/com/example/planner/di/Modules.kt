package com.example.planner.di

import com.example.planner.domain.plan.*
import com.example.planner.ui.main.MainViewModel
import com.example.planner.ui.main.plan.PlanViewModel
import com.example.planner.ui.main.plandetails.PlanDetailsViewModel
import com.example.planner.ui.settings.SettingsViewModel
import com.example.planner.ui.welcome.WelcomeViewModel
import com.example.planner.utils.getDaoSession
import com.example.planner.utils.getDefaultSharedPreferences
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {
    // Resources
    single { androidApplication().resources }
    // SharedPreferences
    single { getDefaultSharedPreferences(androidContext()) /*PreferenceManager.getDefaultSharedPreferences(androidContext)*/ }
    // PreferencesManager
    single { PreferencesManager(get()) }
    // DaoSession
    single { getDaoSession(androidContext()) /*(androidApplication() as PlannerApplication).daoSession*/ }
    // PlanManager
    single<PlanManager> { PlanManagerImpl(get()) }
    // PlanRepository
    single<PlanRepository> { PlanRepositoryImpl() }

    // ViewModel
    viewModel { WelcomeViewModel() }
    viewModel { MainViewModel() }
    viewModel { PlanViewModel(get(), get(), get()) }
    viewModel { (planId: Long) -> PlanDetailsViewModel(planId, get(), get()) }
    viewModel { SettingsViewModel(get()) }
}
