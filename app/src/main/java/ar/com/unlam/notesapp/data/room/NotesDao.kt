package ar.com.unlam.notesapp.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import ar.com.unlam.notesapp.domain.model.Note

@Dao
interface NotesDao {

    @Insert
    fun addNote(entity: NoteEntity)

    @Query("SELECT * FROM NOTE")
    fun getAll(): List<Note>

    @Query("Select * from NOTE where id=:idNote")
    fun getById(idNote: Long): Note

    @Update
    fun update(note: NoteEntity)

    @Delete
    fun deleteNote(note: NoteEntity)
    //    fun getAll(): LiveData<List<Note>>

/*    @Query("Select * from NOTE where id=:idNote")
    fun getById(idNote:Long): List<NoteEntity>*/


}