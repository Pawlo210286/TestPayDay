package com.example.testpayday.core.library.recycler

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.testpayday.core.library.livedata.setDefault

class RecyclerViewConfig<T : Any>(
    private val adapter: RecyclerView.Adapter<*>,
    private val dataSource: LiveData<List<T>>? = null,
    private val decorators: List<RecyclerView.ItemDecoration> = emptyList(),
    private val layoutManager: RecyclerView.LayoutManager? = null
) {

    private val _currentCount = MutableLiveData<Int>().setDefault(0)
    val currentCount: LiveData<Int> = _currentCount

    private val dataObserver = Observer<List<T>> {
        if (checkType<Submittable<T>>(adapter)) {
            (adapter as Submittable<T>).submitList(it)
            _currentCount.postValue(it.size)
        }
    }

    private inline fun <reified T> checkType(obj: Any): Boolean {
        return (obj is T)
    }

    fun attachTo(view: RecyclerView) {
        layoutManager?.let {
            view.layoutManager = it
        }
        view.adapter = adapter

        for (i in 0 until view.itemDecorationCount) {
            view.removeItemDecorationAt(0)
        }

        decorators.forEach {
            view.addItemDecoration(it)
        }

        dataSource?.observeForever(dataObserver)
    }

    fun clear() {
        dataSource?.removeObserver(dataObserver)
    }
}
