package ar.com.unlam.notesapp.data.room

import ar.com.unlam.notesapp.data.room.entities.NoteEntity
import ar.com.unlam.notesapp.domain.model.Note
import ar.com.unlam.notesapp.domain.model.NoteRepository

class RoomNoteRepository(private val notesDao: NotesDao) : NoteRepository {

    override suspend fun addNote(note: Note) {
        val entity = NoteEntity(
            titulo = note.titulo,
            comentario = note.comentario,
            creationTime = note.creationTime
            //modifidedTime = note.creationTime,
          //  removeTime = null
        )
        notesDao.addNote(entity)
    }

    override suspend fun getMyNotes(): List<Note> {
        return notesDao.getAll().map { Note(it.id, it.titulo, it.comentario, it.creationTime ?: 0) }
     //   return notesDao.getAll().map { Note(it.id, it.titulo, it.comentario, it.creationTime ?: 0,it.modifidedTime,it.removeTime) }

    }

    override suspend fun getNoteById(idNote: Long): Note {
        return notesDao.getById(idNote)
    }

    override suspend fun updateNote(nota: Note) {
        val entityToUpdate = NoteEntity(
            id = nota.id,
            titulo = nota.titulo,
            comentario = nota.comentario,
            creationTime = nota.creationTime
          //  modifidedTime =  System.currentTimeMillis(),
         //   removeTime = null
        )
        return notesDao.update(entityToUpdate)
    }

    override suspend fun deleteNote(nota: Note) {
        val entityToDelete = NoteEntity(
            id = nota.id,
            titulo = nota.titulo,
            comentario = nota.comentario,
            creationTime = nota.creationTime
          //  modifidedTime =  System.currentTimeMillis(),
         //   removeTime =  System.currentTimeMillis()
        )
        notesDao.deleteNote(entityToDelete)
    }

    override suspend fun logicDelete(idNote: Long) {
        TODO("Not yet implemented")
    }


}
