package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.plcoding.cleanarchitecturenoteapp.feature_note.data.repository.FakeNoteRepository
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeleteNoteTest {

    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var deleteNote: DeleteNote
    private lateinit var getNote: GetNote
    private lateinit var notes: MutableList<Note>

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        deleteNote = DeleteNote(fakeNoteRepository)
        getNote = GetNote(fakeNoteRepository)

        notes = mutableListOf()
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
                fakeNoteRepository.insertNote(
                    it
                )
            }
        }

    }

    @Test
    fun `delete note by id, correct`() = runBlocking {
        val note = notes[0]

        deleteNote(note)

        assertThat(note.id?.let { getNote(it) }).isNull()
    }
}