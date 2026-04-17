package com.example.finalprojectquicknotes

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.finalprojectquicknotes.screens.HomeScreen
import com.example.finalprojectquicknotes.screens.NotebookUI
import com.example.finalprojectquicknotes.screens.QuickNotesDrawer
import com.example.finalprojectquicknotes.viewmodel.HomeScreenViewModel
import com.example.finalprojectquicknotes.viewmodel.NavigationBarViewModel
import com.example.finalprojectquicknotes.viewmodel.NotebookViewModel
import kotlinx.coroutines.launch

// Navigation.kt
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Notebook : Screen("notebook")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Home.route) {

        composable(Screen.Home.route) {
            val homeViewModel: HomeScreenViewModel = viewModel()
            val drawerViewModel: NavigationBarViewModel = viewModel()
            val drawerState = rememberDrawerState(DrawerValue.Closed)
            val scope = rememberCoroutineScope()

            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    QuickNotesDrawer(
                        viewModel = drawerViewModel,
                        onClose = { scope.launch { drawerState.close() } },
                        onNavigate = { scope.launch { drawerState.close() } }
                    )
                }
            ) {
                HomeScreen(
                    viewModel = homeViewModel,
                    onNoteClick = { navController.navigate(Screen.Notebook.route) },
                    onAddNote = { navController.navigate(Screen.Notebook.route) },
                    onOpenDrawer = { scope.launch { drawerState.open() } }
                )
            }
        }
        composable(Screen.Notebook.route) {
            val viewModel: NotebookViewModel = viewModel()
            NotebookUI(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}