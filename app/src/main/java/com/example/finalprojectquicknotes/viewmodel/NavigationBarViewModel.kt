package com.example.finalprojectquicknotes.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NavigationBarViewModel : ViewModel() {
    private val _selectedItem = MutableStateFlow("all_notes")
    val selectedItem: StateFlow<String> = _selectedItem.asStateFlow()

    fun onItemSelected(item: String) {
        _selectedItem.value = item
    }
}