package com.example.myapplication.data

import android.util.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import java.util.*
import java.util.concurrent.TimeUnit

object OperationsQueue {

    data class Command(val value: Int)

    private val queue: Queue<Command> = LinkedList()

    private var queueIsBusy = false

    private val compositeDisposable = CompositeDisposable()

    fun addToQueue(command: Command) {
        queue.add(command)
        runNextIfPossible()
    }

    fun clear() {
        compositeDisposable.clear()
    }

    private fun runNextIfPossible() {

        if (queueIsBusy) return

        val command = queue.poll()
        if (command != null) {
            queueIsBusy = true
            Single.timer(command.value.toLong(), TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        Log.d(TAG, "${command.value}")
                        queueIsBusy = false
                        runNextIfPossible()
                    },
                    onError = { throw RuntimeException("runNextCommand error: $it") })
                .addTo(compositeDisposable)
        }
    }

    private const val TAG = "OperationsQueue"
}