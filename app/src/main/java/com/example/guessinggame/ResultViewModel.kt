package com.example.guessinggame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ResultViewModel(private val finalResult: String) : ViewModel(){
    //set up live data
    private val _result = MutableLiveData<String>()
    val result: LiveData<String> get() = _result

    init{
        _result.value = finalResult
    }
}