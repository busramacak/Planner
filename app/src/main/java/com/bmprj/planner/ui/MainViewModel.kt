package com.bmprj.planner.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor():ViewModel() {

    private var _fabClickEvent = MutableStateFlow<Boolean>(false)
    val fabClickEvent  = _fabClickEvent.asStateFlow()


    fun onFabClicked()  = viewModelScope.launch{
        _fabClickEvent.value = true
    }

    fun makeFabValueDefault() = viewModelScope.launch{
        _fabClickEvent.value = false
    }
}