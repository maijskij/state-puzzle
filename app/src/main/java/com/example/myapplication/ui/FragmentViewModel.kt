package com.example.myapplication.ui

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.ConnectionState
import com.example.myapplication.data.OperationsQueue
import com.example.myapplication.data.StateGenerator
import com.example.myapplication.ui.utils.SingleLiveEvent
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy

class FragmentViewModel(
    private val operationsQueue: OperationsQueue,
    private val stateGenerator: StateGenerator,
    private val uiScheduler: Scheduler
) : ViewModel() {

    val connectionState = SingleLiveEvent<ConnectionState>()
    val checkConnection = SingleLiveEvent<Unit>()

    private val compositeDisposable = CompositeDisposable()
    private var currentState: ConnectionState? = null

    init {
        startStateListening()
    }

    public override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        operationsQueue.clear()
    }

    fun onCheckConnectionClicked() {
        if (currentState == ConnectionState.CONNECTION_ESTABLISHED) {
            checkConnection.value = Unit
        }
    }

    fun populateOperationQueue() {
        for (id in 1..10) {
            operationsQueue.addToQueue(OperationsQueue.Command(id))
        }
    }

    private fun startStateListening() {
        stateGenerator.connectableStateObservable
            .observeOn(uiScheduler)
            .subscribeBy(
                onNext = {
                    val newState = ConnectionState.from(it)
                    currentState = newState
                    connectionState.value = newState
                },
                onError = { throw RuntimeException("State generation error: $it") }
            ).addTo(compositeDisposable)
    }
}