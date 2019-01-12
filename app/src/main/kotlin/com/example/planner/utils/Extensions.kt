package com.example.planner.utils

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.Integer.toHexString

fun Any.getDescription() =
    "### ${this::class.java.simpleName} : ${toHexString(this.hashCode())} ###"

fun Completable.applyIoScheduler(): Completable =
    subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.applyIoScheduler(): Single<T> =
    subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
