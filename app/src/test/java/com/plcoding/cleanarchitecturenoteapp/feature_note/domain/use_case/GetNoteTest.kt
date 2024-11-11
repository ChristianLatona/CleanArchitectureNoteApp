package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.plcoding.cleanarchitecturenoteapp.feature_note.data.repository.FakeNoteRepository
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNoteTest {

    private lateinit var getNote: GetNote
    private lateinit var fakeRepository: FakeNoteRepository

    @Before
    fun setUp() {
        fakeRepository = FakeNoteRepository()
        getNote = GetNote(fakeRepository)

        val notes = mutableListOf<Note>()
        for (k in 1 .. 10) {
            notes.add(
                Note(
                    color = k,
                    content = "content $k",
                    timestamp = 1L,
                    title = "title $k",
                    id = k
                )
            )
        }

        runBlocking {
            notes.forEach{
                fakeRepository.insertNote(
                    it
                )
            }
        }

    }

    @Test
    fun `get note by id, correct`() = runBlocking {
        val note = getNote(1)
        assertThat(note?.title).isEqualTo("title 1")
    }
}