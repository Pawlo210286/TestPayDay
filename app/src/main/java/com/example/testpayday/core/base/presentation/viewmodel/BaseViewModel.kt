package com.example.testpayday.core.base.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

abstract class BaseViewModel : ViewModel() {
    private val job = Job()
    protected val scope = CoroutineScope(Dispatchers.IO + job)
    val eventsQueue = EventsQueue()

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}
