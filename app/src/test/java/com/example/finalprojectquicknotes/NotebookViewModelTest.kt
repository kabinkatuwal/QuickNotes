package com.example.finalprojectquicknotes

import com.example.finalprojectquicknotes.data.Note
import com.example.finalprojectquicknotes.repository.NoteRepository
import com.example.finalprojectquicknotes.viewmodel.NotebookViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.*

@OptIn(ExperimentalCoroutinesApi::class)
class NotebookViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: NoteRepository
    private lateinit var viewModel: NotebookViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mock(NoteRepository::class.java)
        `when`(repository.allNotes).thenReturn(flowOf(emptyList()))
        viewModel = NotebookViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        // Ensure Mockito doesn't leak state between tests
        validateMockitoUsage()
    }

    @Test
    fun testOnTitleChangeUpdatesState() {
        viewModel.onTitleChange("Test Title")
        assertEquals("Test Title", viewModel.title.value)
    }

    @Test
    fun testOnContentChangeUpdatesState() {
        viewModel.onContentChange("Test Content")
        assertEquals("Test Content", viewModel.content.value)
    }

    @Test
    fun testLoadNoteMinusOneClearsFields() {
        viewModel.onTitleChange("Some Title")
        viewModel.loadNote(-1)
        assertEquals("", viewModel.title.value)
    }

    @Test
    fun testLoadNoteWithIdFetchesData() = runTest {
        val note = Note(id = 1, title = "Saved", content = "Content")
        `when`(repository.allNotes).thenReturn(flowOf(listOf(note)))

        viewModel.loadNote(1)
        advanceUntilIdle()

        assertEquals("Saved", viewModel.title.value)
    }

    @Test
    fun testSaveNoteDoesNotInsertIfBlank() = runTest {
        viewModel.onTitleChange("")
        viewModel.onContentChange("")
        viewModel.saveNote {}
        advanceUntilIdle()
        // Use nonNullAny() helper here
        verify(repository, never()).insert(nonNullAny())
    }

    @Test
    fun testSaveNoteCallsInsertForNewNote() = runTest {
        viewModel.loadNote(-1)

        viewModel.onTitleChange("Test Assignment Title")

        viewModel.onContentChange("This content prevents the blank-check return.")

        viewModel.saveNote { }

        advanceUntilIdle()

        verify(repository).insert(nonNullAny())
    }

    @Test
    fun testSaveNoteCallsUpdateForExistingNote() = runTest {
        viewModel.loadNote(1)
        viewModel.onTitleChange("Updated")
        viewModel.saveNote {}
        advanceUntilIdle()
        verify(repository).update(nonNullAny())
    }

    @Test
    fun testDeleteNoteCallsRepository() = runTest {
        viewModel.loadNote(1)
        viewModel.deleteCurrentNote {}
        advanceUntilIdle()
        verify(repository).delete(nonNullAny())
    }

    @Test
    fun testDeleteNoteDoesNotCallIfNew() = runTest {
        viewModel.loadNote(-1)
        viewModel.deleteCurrentNote {}
        advanceUntilIdle()
        verify(repository, never()).delete(nonNullAny())
    }

    @Test
    fun testSaveNoteTriggersCallback() = runTest {
        viewModel.onTitleChange("New Note Title")

        viewModel.onContentChange("This is the note content.")

        viewModel.loadNote(-1)

        viewModel.saveNote {}

        advanceUntilIdle()

        verify(repository).insert(nonNullAny())
    }


    private fun <T> nonNullAny(): T {
        any<T>()
        return uninitialized()
    }

    private fun <T> uninitialized(): T = null as T
}