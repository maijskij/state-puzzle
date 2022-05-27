package com.example.myapplication.data

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observables.ConnectableObservable
import java.util.concurrent.TimeUnit
import kotlin.random.Random

object StateGenerator {

    val connectableStateObservable: ConnectableObservable<Int> =
        Observable.interval(5000, TimeUnit.MILLISECONDS)
            .flatMap { Observable.fromCallable { nextRandomNotAsPreviousNumber() } }
            .publish()

    init {
        connectableStateObservable.connect()
    }

    private var previousValue = -1

    private fun nextRandomNotAsPreviousNumber(): Int {
        var newValue = nextRandomNumber()
        while (newValue == previousValue) {
            newValue = nextRandomNumber()
        }
        previousValue = newValue
        return newValue
    }

    private fun nextRandomNumber() = Random.nextInt(0, ConnectionState.values().size)
}