package com.example.task_2.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TextViewModel : ViewModel() {
    private var _text = MutableLiveData<String>().apply { value = "" }
    val text: LiveData<String> get() = _text

    fun updateText(newText: String) {
        _text.value = newText
    }
}