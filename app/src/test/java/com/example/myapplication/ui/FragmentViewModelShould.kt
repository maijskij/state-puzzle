package com.example.myapplication.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myapplication.data.ConnectionState
import com.example.myapplication.data.OperationsQueue
import com.example.myapplication.data.StateGenerator
import com.example.myapplication.ui.utils.getOrAwaitValue
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*


@RunWith(MockitoJUnitRunner::class)
class FragmentViewModelShould {

    private lateinit var viewModel: FragmentViewModel

    @Mock
    private lateinit var operationsQueueMock: OperationsQueue

    @Mock
    private lateinit var stateGeneratorMock: StateGenerator

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val behaviourSubject = BehaviorSubject.create<Int>()
    private val connectableObservable = behaviourSubject.publish()

    @Before
    fun setUp() {
        whenever(stateGeneratorMock.connectableStateObservable) doReturn connectableObservable
        connectableObservable.connect()
        viewModel =
            FragmentViewModel(operationsQueueMock, stateGeneratorMock, Schedulers.trampoline())
    }

    @Test
    fun propagateConnectionStateViaLiveData() {
        behaviourSubject.onNext(ConnectionState.CONNECTION_ERROR.value)

        val state = viewModel.connectionState.getOrAwaitValue()
        assertThat(state).isEqualTo(ConnectionState.CONNECTION_ERROR)
    }

    @Test
    fun populateOperationsQueue() {
        viewModel.populateOperationQueue()

        verify(operationsQueueMock, times(10)).addToQueue(any())
    }

    @Test
    fun clearQueueDisposable() {
        viewModel.onCleared()

        verify(operationsQueueMock).clear()
    }

    @Test
    fun onCheckConnectionClicked() {
        behaviourSubject.onNext(ConnectionState.CONNECTION_ESTABLISHED.value)
        viewModel.onCheckConnectionClicked()

        val state = viewModel.checkConnection.getOrAwaitValue()
        assertThat(state).isEqualTo(Unit)
    }
}