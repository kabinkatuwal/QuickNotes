package com.example.finalprojectquicknotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectquicknotes.data.Note
import com.example.finalprojectquicknotes.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class NotebookViewModel(private val repository: NoteRepository) : ViewModel() {
    private var currentNoteId: Int = -1
    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()
    private val _content = MutableStateFlow("")
    val content = _content.asStateFlow()

    fun onTitleChange(newTitle: String) {
        _title.value = newTitle
    }

    fun onContentChange(newContent: String) {
        _content.value = newContent
    }

    fun loadNote(id: Int) {
        currentNoteId = id
        if (id != -1) {
            viewModelScope.launch {
                repository.allNotes.firstOrNull()?.find { it.id == id }?.let {
                    _title.value = it.title
                    _content.value = it.content
                }
            }
        } else {
            _title.value = ""
            _content.value = ""
        }
    }

    fun saveNote(onComplete: () -> Unit) {
        if (_title.value.isBlank() && _content.value.isBlank()) return
        viewModelScope.launch {
            val note = Note(
                id = if (currentNoteId == -1) 0 else currentNoteId,
                title = _title.value,
                content = _content.value
            )
            if (currentNoteId == -1) repository.insert(note) else repository.update(note)
            onComplete()
        }
    }

    fun deleteCurrentNote(onComplete: () -> Unit) {
        if (currentNoteId != -1) {
            viewModelScope.launch {
                val noteToDelete = Note(
                    id = currentNoteId,
                    title = _title.value,
                    content = _content.value
                )
                repository.delete(noteToDelete)
                onComplete()
            }
        }
    }
}