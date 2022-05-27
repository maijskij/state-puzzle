package com.example.myapplication.ui.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.OperationsQueue
import com.example.myapplication.data.StateGenerator
import com.example.myapplication.ui.FragmentViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler


class FragmentViewModelFactory(
    private val operationsQueue: OperationsQueue,
    private val stateGenerator: StateGenerator,
    private val uiScheduler: Scheduler
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        FragmentViewModel(operationsQueue, stateGenerator, uiScheduler) as T


    companion object {
        fun createFactory() = FragmentViewModelFactory(
            OperationsQueue,
            StateGenerator,
            AndroidSchedulers.mainThread()
        )
    }
}