package ar.com.unlam.notesapp.domain.model

interface NotesRepository {

    fun addNote(note: Note)

    fun getMyNotes(): List<Note>

    fun getNoteById(nombre: String): Note

    fun updateNote(nota: Note)
}