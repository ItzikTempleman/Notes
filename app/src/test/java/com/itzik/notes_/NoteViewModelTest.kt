package com.itzik.notes_

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.itzik.notes_.project.model.Note
import com.itzik.notes_.project.repositories.AppRepositoryInterface
import com.itzik.notes_.project.viewmodels.NoteViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule


class NoteViewModelTest {


        @get:Rule
        val instantTaskExecutorRule = InstantTaskExecutorRule()

        @get:Rule
        val mockitoRule: MockitoRule = MockitoJUnit.rule()

        @Mock
        private lateinit var mockRepository: AppRepositoryInterface
        private lateinit var mockNoteViewModel: NoteViewModel
        private val testDispatcher = StandardTestDispatcher()

        @OptIn(ExperimentalCoroutinesApi::class)
        @Before
        fun setup() {
            Dispatchers.setMain(testDispatcher)
            mockNoteViewModel = NoteViewModel(mockRepository)
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        @After
        fun tearDown() {
            Dispatchers.resetMain()
            testDispatcher.cancel()
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        @Test
        fun `fetchNotesForUser get correct note `() = runTest {

            val userId = "10000001"
            val sampleNoteListResponse = mutableListOf(
                Note(
                    userId = userId, content = "Hello", title = "wergqerh", noteId = 0
                ),
                Note(
                    userId = userId, content = "Itzik",title = "wer", noteId = 1
                )
            )
            `when`(mockRepository.fetchNotes(userId)).thenReturn(sampleNoteListResponse)
            mockNoteViewModel.fetchNotesForUser(userId)
            advanceUntilIdle()

            val result = mockNoteViewModel.publicNote.value

            assertEquals("10000001",userId)
            assertEquals("How are you",result.content)
        }
    }