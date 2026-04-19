package com.example.finalprojectquicknotes


import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.finalprojectquicknotes.ui.theme.FinalProjectQuickNotesTheme
import com.example.finalprojectquicknotes.viewmodel.NoteViewModel
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

class NoteAppUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testHomeScreen_TitleIsVisible() {
        startApp()
        composeTestRule.onNodeWithText("Quick Notes").assertIsDisplayed()
    }

    @Test
    fun testHomeScreen_AddButtonIsVisible() {
        startApp()
        composeTestRule.onNodeWithContentDescription("Add note").assertIsDisplayed()
    }

    @Test
    fun testNavigation_ClickAddOpensNotebook() {
        startApp()
        composeTestRule.onNodeWithContentDescription("Add note").performClick()
        composeTestRule.onNodeWithText("Title").assertIsDisplayed()
    }

    @Test
    fun testNotebook_TitleInputWorks() {
        startApp()
        composeTestRule.onNodeWithContentDescription("Add note").performClick()
        composeTestRule.onNodeWithText("Title").performTextInput("School Project")
        composeTestRule.onNodeWithText("School Project").assertIsDisplayed()
    }

    @Test
    fun testNotebook_ContentInputWorks() {
        startApp()
        composeTestRule.onNodeWithContentDescription("Add note").performClick()
        composeTestRule.onNodeWithText("Start writing your thoughts...")
            .performTextInput("Finish SODV3203")
        composeTestRule.onNodeWithText("Finish SODV3203").assertIsDisplayed()
    }

    @Test
    fun testNotebook_SaveButtonIsVisible() {
        startApp()
        composeTestRule.onNodeWithContentDescription("Add note").performClick()
        composeTestRule.onNodeWithText("Save").assertIsDisplayed()
    }

    @Test
    fun testNotebook_BackButtonWorks() {
        startApp()
        composeTestRule.onNodeWithContentDescription("Add note").performClick()
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        composeTestRule.onNodeWithText("Quick Notes").assertIsDisplayed()
    }

    @Test
    fun testHomeScreen_DrawerOpens() {
        startApp()
        composeTestRule.onNodeWithContentDescription("Menu").performClick()
        composeTestRule.onNodeWithText("All notes").assertIsDisplayed()
    }

    @Test
    fun testHomeScreen_SearchBarIsVisible() {
        startApp()
        composeTestRule.onNodeWithText("Search your thoughts...").assertIsDisplayed()
    }

    @Test
    fun testHomeScreen_SearchInputWorks() {
        startApp()
        composeTestRule.onNodeWithText("Search your thoughts...").performTextInput("Meeting")
        composeTestRule.onNodeWithText("Meeting").assertIsDisplayed()
    }

    private fun startApp() {

        val context =
            androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().targetContext
        val database = com.example.finalprojectquicknotes.data.NoteDatabase.getDatabase(context)
        val repository =
            com.example.finalprojectquicknotes.repository.NoteRepository(database.noteDao())
        val factory =
            com.example.finalprojectquicknotes.viewmodel.NoteViewModel.ViewModelFactory(repository)

        composeTestRule.setContent {
            com.example.finalprojectquicknotes.ui.theme.FinalProjectQuickNotesTheme {
                AppNavigation(factory = factory)
            }
        }
    }
}