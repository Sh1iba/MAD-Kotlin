package com.example.task_2.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CounterViewModel : ViewModel() {
    private var _counter = MutableLiveData<Int>().apply { value = 0 }
    val counter: LiveData<Int> get() = _counter

    fun incrementCounter() {
        _counter.value = (_counter.value ?: 0) + 1
    }
}

//ViewModel: Компонент, который хранит и управляет состоянием
//пользовательского интерфейса