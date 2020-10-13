package ar.com.unlam.notesapp.domain.model

import ar.com.unlam.notesapp.domain.Note

interface NotesRepository {

    fun addNote(note:Note)

    fun getMyNotes(): List<Note>
}