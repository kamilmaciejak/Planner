package com.example.planner.ui

import android.content.res.Resources
import android.databinding.Observable
import android.databinding.ObservableBoolean
import android.databinding.ObservableField

class OnPropertyChangedCallback(
    private val onPropertyChanged: () -> Unit
) : Observable.OnPropertyChangedCallback() {
    override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
        onPropertyChanged()
    }
}

class ValidationField<T>(
    initialValue: T?,
    private val validate: (T?) -> Int?,
    private val resources: Resources
) {
    val value = ObservableField<T>(initialValue)
    val error = ObservableField<String>()
    private var shouldValidate = ObservableBoolean()

    init {
        addOnPropertyChangedCallback()
    }

    private fun addOnPropertyChangedCallback() {
        value.addOnPropertyChangedCallback(OnPropertyChangedCallback {
            if (shouldValidate.get()) {
                validateValue()
            }
        })
    }

    fun onFocusChange(hasFocus: Boolean) {
        if (!hasFocus) {
            validate()
        }
    }

    fun validate() {
        validateValue()
        if (!shouldValidate.get()) {
            shouldValidate.set(true)
        }
    }

    private fun validateValue() {
        error.set(validate(value.get())?.let { resId ->
            resources.getString(resId)
        })
    }

    fun get() = value.get()

    fun set(value: T?) {
        this.value.set(value)
    }

    fun isValid() = error.get() == null
}
