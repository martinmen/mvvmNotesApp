package ar.com.unlam.notesapp.domain.model

interface NoteRepository {

    fun addNote(note: Note)

    fun getMyNotes(): List<Note>

    fun getNoteById(idNote: Long): Note

    fun updateNote(nota: Note)

    fun deleteNote(nota: Note)
}