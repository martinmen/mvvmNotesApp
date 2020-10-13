package ar.com.unlam.notesapp.data.room

import ar.com.unlam.notesapp.domain.Note
import ar.com.unlam.notesapp.domain.model.NotesRepository

class RoomNoteRepository(private val notesDao: NotesDao) : NotesRepository{

    override fun addNote(note: Note) {
        val entity = NoteEntity(
            nombre = note.nombre,
            comentario = note.comentario
        )
        notesDao.addNote(entity)
    }

    override fun getMyNotes(): List<Note> {
      return  notesDao.getAll()
    }
}