package com.example.planner.ui.main

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.example.planner.utils.getDescription
import timber.log.Timber

class MainViewModel : ViewModel() {
    val text = ObservableField("MainViewModel : ${hashCode()}")

    init {
        Timber.d(getDescription())
    }
}