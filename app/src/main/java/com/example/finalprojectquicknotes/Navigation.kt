package com.example.finalprojectquicknotes

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.finalprojectquicknotes.screens.HomeScreen
import com.example.finalprojectquicknotes.screens.NotebookUI
import com.example.finalprojectquicknotes.screens.QuickNotesDrawer
import com.example.finalprojectquicknotes.viewmodel.HomeScreenViewModel
import com.example.finalprojectquicknotes.viewmodel.NavigationBarViewModel
import com.example.finalprojectquicknotes.viewmodel.NoteViewModel
import com.example.finalprojectquicknotes.viewmodel.NotebookViewModel
import kotlinx.coroutines.launch

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Notebook : Screen("notebook/{noteId}") {
        fun createRoute(noteId: Int) = "notebook/$noteId"
    }
}

@Composable
fun AppNavigation(factory: NoteViewModel.ViewModelFactory) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            val homeViewModel: HomeScreenViewModel = viewModel(factory = factory)
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
                    onNoteClick = { note ->
                        navController.navigate(Screen.Notebook.createRoute(note.id))
                    },
                    onAddNote = {
                        navController.navigate(Screen.Notebook.createRoute(-1))
                    },
                    onOpenDrawer = { scope.launch { drawerState.open() } }
                )
            }
        }
        composable(
            route = Screen.Notebook.route,
            arguments = listOf(navArgument("noteId") { type = NavType.IntType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId") ?: -1
            val notebookViewModel: NotebookViewModel = viewModel(factory = factory)

            LaunchedEffect(noteId) {
                notebookViewModel.loadNote(noteId)
            }

            NotebookUI(
                viewModel = notebookViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}