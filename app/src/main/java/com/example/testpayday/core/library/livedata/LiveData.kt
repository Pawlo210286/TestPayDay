package com.example.testpayday.core.library.livedata

import androidx.fragment.app.Fragment
import androidx.lifecycle.*

fun <T> MutableLiveData<T>.onNext(next: T) {
    this.postValue(next)
}

fun <T : Any> LiveData<T>.requireValue(): T = checkNotNull(value)

inline fun <reified T, LD : LiveData<T>> Fragment.observe(liveData: LD, crossinline block: (T) -> Unit) {
    liveData.observe(viewLifecycleOwner, Observer<T> { block(it) })
}

inline fun <X, Y> LiveData<X>.mapDistinct(crossinline transform: (X) -> Y): LiveData<Y> {
    return map(transform).distinctUntilChanged()
}

fun <T> MutableLiveData<T>.setDefault(value: T): MutableLiveData<T> {
    return this.apply {
        postValue(value)
    }
}

fun <X, Y> LiveData<X>.distinctUntilChanged(transform: (X) -> Y): LiveData<X> {
    val outputLiveData = MediatorLiveData<X>()
    outputLiveData.addSource(this, object : Observer<X> {
        var firstTime = true
        override fun onChanged(currentValue: X) {
            val previousValue = outputLiveData.value
            val valueAppeared = firstTime || previousValue == null && currentValue != null
            if (valueAppeared ||
                previousValue != null && transform(previousValue) != transform(currentValue)
            ) {
                firstTime = false
                outputLiveData.value = currentValue
            }
        }
    })
    return outputLiveData
}
