package ar.com.unlam.notesapp.data.room

import androidx.room.*
import ar.com.unlam.notesapp.data.room.entities.NoteEntity
import ar.com.unlam.notesapp.domain.model.Note

@Dao
interface NotesDao {

    @Insert
    suspend fun addNote(entity: NoteEntity)

    @Query("SELECT * FROM NOTE where note.removeTime is null")
    suspend fun getAll(): List<Note>

    @Query("Select * from NOTE where id=:idNote")
    suspend fun getById(idNote: Long): Note

    @Update
    suspend fun update(note: NoteEntity)

    @Update
    suspend fun deleteNote(note: NoteEntity)

}