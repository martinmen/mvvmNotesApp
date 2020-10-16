package ar.com.unlam.notesapp.data.room

import ar.com.unlam.notesapp.domain.model.Note
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
      return  notesDao.getAll().map { Note(it.nombre,it.comentario) }
    }

    override fun getNoteById(nombre:String): Note{
        return  notesDao.getById(nombre) }


    override fun updateNote(nota:Note){
        return  notesDao.update(nota as NoteEntity) }
    }
