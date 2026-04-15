package com.example.finalprojectquicknotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.NavigationBar
import com.example.finalprojectquicknotes.screens.HomeScreen
import com.example.finalprojectquicknotes.ui.theme.FinalProjectQuickNotesTheme
import com.example.finalprojectquicknotes.screens.NotebookUI

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FinalProjectQuickNotesTheme {
//                HomeScreen()
                NotebookUI()
            }
        }
    }
}

