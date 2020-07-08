package com.example.testpayday.core.library.recycler

interface Submittable<T : Any> {
    fun submitList(list: List<T>)
}
