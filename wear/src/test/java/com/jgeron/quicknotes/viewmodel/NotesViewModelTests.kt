package com.jgeron.quicknotes.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jgeron.quicknotes.data.FakeStorage
import com.jgeron.quicknotes.data.Note
import org.junit.*
import org.junit.runner.RunWith

/**
 *Local unit test of NotesViewModel.
 */

class NotesViewModelTests {

    private lateinit var out: NotesViewModel

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule();

    @Before
    fun setup(){
        out = NotesViewModel(FakeStorage())
    }

    @Test
    fun `add note, return list length 1`() {
        out.addNote("Sample 1")
        out.updateNotesListFromStore()
        Assert.assertTrue("List isn't size 1", out.notesList.size == 1)
        Assert.assertTrue("noteLiveData value is wrong", out.noteLiveData.value == out.notesList[0])
    }

    @Test
    fun `remove only note, return list length 0`() {
        out.addNote("Sample 1")
        val note = out.notesList[0]
        out.removeNote(out.notesList[0].id)
        out.updateNotesListFromStore()
        Assert.assertTrue("List isn't empty", out.notesList.size == 0)
        Assert.assertTrue("noteLiveData value is wrong", out.noteLiveData.value == note)
    }

    private fun createSampleNote(): Note{
        return Note("1", "Sample 1", "Some Sample Description")
    }

    @After
    fun tearDown(){

    }

}