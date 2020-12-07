package ar.com.unlam.notesapp.data.room.repositories

import ar.com.unlam.notesapp.R
import ar.com.unlam.notesapp.data.exceptions.RepositoryException
import ar.com.unlam.notesapp.data.room.NotesDao
import ar.com.unlam.notesapp.data.room.entities.NoteEntity
import ar.com.unlam.notesapp.domain.model.Note
import java.lang.Exception

class RoomNoteRepositoryImpl(private val notesDao: NotesDao) : NoteRepository {

    override suspend fun addNote(note: Note) {
        try {
            val entity = NoteEntity(
                titulo = note.titulo,
                comentario = note.comentario,
                provincia = note.provincia,
                municipio = note.municipio,
                imagen = note.imagen,
                creationTime = note.creationTime,
                modifidedTime = note.creationTime,
                removeTime = null
            )
            notesDao.addNote(entity)
        } catch (e: Exception) {
            throw RepositoryException((R.string.error_insert_throw).toString(), e)
        }
    }

    override suspend fun getMyNotes(): List<Note> {
        try {
            return notesDao.getAll().map {
                Note(
                    it.id,
                    it.titulo,
                    it.comentario,
                    it.provincia,
                    it.municipio,
                    it.imagen,
                    it.creationTime!! ,
                    it.modifidedTime,
                    it.removeTime
                )
            }
        } catch (e: Exception) {
            throw RepositoryException((R.string.error_getNotes_throw).toString(), e)
        }
    }


override suspend fun getNoteById(idNote: Long): Note {
   try {
       return notesDao.getById(idNote)
   } catch (e: Exception) {
       throw RepositoryException((R.string.error_getById_throw).toString(), e)
   }
}

override suspend fun updateNote(note: Note) {
   try {
       val entityToUpdate = NoteEntity(
           id = note.id,
           titulo = note.titulo,
           comentario = note.comentario,
           provincia = note.provincia,
           municipio = note.municipio,
           imagen = note.imagen,
           creationTime = note.creationTime,
           modifidedTime = System.currentTimeMillis(),
           removeTime = null
       )
       return notesDao.update(entityToUpdate)
   } catch (e: Exception) {
       throw RepositoryException(R.string.error_update_throw.toString(), e)
   }
}

override suspend fun deleteNote(note: Note) {
  try {
      val entityToDelete = NoteEntity(
          id = note.id,
          titulo = note.titulo,
          comentario = note.comentario,
          provincia = note.provincia,
          municipio = note.municipio,
          imagen = note.imagen,
          creationTime = note.creationTime,
          modifidedTime = note.modifidedTime,
          removeTime = System.currentTimeMillis()
      )
      notesDao.deleteNote(entityToDelete)
  } catch (e: Exception) {
      throw RepositoryException(R.string.error_delete_throw.toString(), e)
  }
}

override suspend fun undoDeleteNote(note: Note) {
    try {
        val entityToDelete = NoteEntity(
            id = note.id,
            titulo = note.titulo,
            comentario = note.comentario,
            provincia = note.provincia,
            municipio = note.municipio,
            imagen = note.imagen,
            creationTime = note.creationTime,
            modifidedTime = note.modifidedTime,
            removeTime = null
        )
        notesDao.deleteNote(entityToDelete)
    }catch (e: Exception) {
        throw RepositoryException(R.string.error_undodelete_throw.toString(), e)
    }
}

}
