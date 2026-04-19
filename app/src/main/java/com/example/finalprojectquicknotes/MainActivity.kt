package com.example.finalprojectquicknotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.NavigationBar
import com.example.finalprojectquicknotes.data.NoteDatabase
import com.example.finalprojectquicknotes.repository.NoteRepository
import com.example.finalprojectquicknotes.screens.HomeScreen
import com.example.finalprojectquicknotes.ui.theme.FinalProjectQuickNotesTheme
import com.example.finalprojectquicknotes.screens.NotebookUI
import com.example.finalprojectquicknotes.screens.QuickNotesDrawer
import com.example.finalprojectquicknotes.viewmodel.NoteViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = NoteDatabase.getDatabase(this)
        val repository = NoteRepository(database.noteDao())

        val factory = NoteViewModel.ViewModelFactory(repository)

        enableEdgeToEdge()
        setContent {
            FinalProjectQuickNotesTheme {
                //HomeScreen()
                //NotebookUI()
               //QuickNotesDrawer()
                AppNavigation(factory =factory)
            }
        }
    }
}

