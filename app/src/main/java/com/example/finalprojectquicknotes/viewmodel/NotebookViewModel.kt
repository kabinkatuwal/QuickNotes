package com.example.finalprojectquicknotes.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NotebookViewModel : ViewModel() {

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()

    private val _content = MutableStateFlow("")
    val content: StateFlow<String> = _content.asStateFlow()

    private val _isSaved = MutableStateFlow(false)
    val isSaved: StateFlow<Boolean> = _isSaved.asStateFlow()

    fun onTitleChange(newTitle: String) {
        _title.value = newTitle
        _isSaved.value = false
    }

    fun onContentChange(newContent: String) {
        _content.value = newContent
        _isSaved.value = false
    }

    fun saveNote() {
        // will hook into repository later
        _isSaved.value = true
    }

    fun clearNote() {
        _title.value = ""
        _content.value = ""
        _isSaved.value = false
    }
}