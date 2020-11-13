package ar.com.unlam.notesapp.data.room.repositories

import ar.com.unlam.notesapp.domain.model.Note

interface NoteRepository {

   suspend fun addNote(note: Note)

   suspend  fun getMyNotes(): List<Note>

   suspend fun getNoteById(idNote: Long): Note

   suspend fun updateNote(nota: Note)

   suspend fun deleteNote(nota: Note)

   suspend  fun logicDelete(idNote: Long)
}