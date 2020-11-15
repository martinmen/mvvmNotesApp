package ar.com.unlam.notesapp.data.room.repositories

import ar.com.unlam.notesapp.data.room.NotesDao
import ar.com.unlam.notesapp.data.room.entities.NoteEntity
import ar.com.unlam.notesapp.domain.model.Note

class RoomNoteRepositoryImpl(private val notesDao: NotesDao) : NoteRepository {

    override suspend fun addNote(note: Note) {
        val entity = NoteEntity(
            titulo = note.titulo,
            comentario = note.comentario,
            provincia = note.provincia,
            municipio = note.municipio,
            creationTime = note.creationTime,
            modifidedTime = note.creationTime,
            removeTime = null
        )
        notesDao.addNote(entity)
    }

    override suspend fun getMyNotes(): List<Note> {
        return notesDao.getAll().map {
            Note(
                it.id,
                it.titulo,
                it.comentario,
                it.provincia ?: "",
                it.municipio ?: "",
                it.creationTime ?: 0,
                it.modifidedTime ?: 0,
                it.removeTime ?: 0
            )
        }
        //   return notesDao.getAll().map { Note(it.id, it.titulo, it.comentario, it.creationTime ?: 0,it.modifidedTime,it.removeTime) }

    }

    override suspend fun getNoteById(idNote: Long): Note {
        return notesDao.getById(idNote)
    }

    override suspend fun updateNote(note: Note) {
        val entityToUpdate = NoteEntity(
            id = note.id,
            titulo = note.titulo,
            comentario = note.comentario,
            provincia = note.provincia,
            municipio = note.municipio,
            creationTime = note.creationTime,
            modifidedTime = System.currentTimeMillis(),
            removeTime = null
        )
        return notesDao.update(entityToUpdate)
    }

    override suspend fun deleteNote(note: Note) {
        val entityToDelete = NoteEntity(
            id = note.id,
            titulo = note.titulo,
            comentario = note.comentario,
            creationTime = note.creationTime,
            modifidedTime = note.modifidedTime,
            removeTime = System.currentTimeMillis()
        )
        notesDao.deleteNote(entityToDelete)
    }

    override suspend fun undoDeleteNote(note: Note) {
        val entityToDelete = NoteEntity(
            id = note.id,
            titulo = note.titulo,
            comentario = note.comentario,
            creationTime = note.creationTime,
            modifidedTime = note.modifidedTime,
            removeTime = null
        )
        notesDao.deleteNote(entityToDelete)    }

}
