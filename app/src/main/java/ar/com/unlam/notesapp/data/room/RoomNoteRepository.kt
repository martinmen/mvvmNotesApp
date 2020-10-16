package ar.com.unlam.notesapp.data.room

import ar.com.unlam.notesapp.domain.model.Note
import ar.com.unlam.notesapp.domain.model.NoteRepository

class RoomNoteRepository(private val notesDao: NotesDao) : NoteRepository {

    override fun addNote(note: Note) {
        val entity = NoteEntity(
            titulo = note.titulo,
            comentario = note.comentario,
            creationTime = note.creationTime
        )
        notesDao.addNote(entity)
    }

    override fun getMyNotes(): List<Note> {
        return notesDao.getAll().map { Note(it.id, it.titulo, it.comentario, it.creationTime ?: 0) }
    }

    override fun getNoteById(idNote: Long): Note {
        return notesDao.getById(idNote)
    }

    override fun updateNote(nota: Note) {
        val entityToUpdate = NoteEntity(
            id = nota.id,
            titulo = nota.titulo,
            comentario = nota.comentario,
            creationTime = nota.creationTime
        )
        return notesDao.update(entityToUpdate)
    }

    override fun deleteNote(nota: Note) {
        val entityToDelete = NoteEntity(
            id = nota.id,
            titulo = nota.titulo,
            comentario = nota.comentario,
            creationTime = nota.creationTime
        )
        notesDao.deleteNote(entityToDelete)
    }


}
