package ar.com.unlam.notesapp.data.room

import androidx.room.*
import ar.com.unlam.notesapp.data.room.entities.NoteEntity
import ar.com.unlam.notesapp.domain.model.Note

@Dao
interface NotesDao {

    @Insert
   suspend fun addNote(entity: NoteEntity)

 //@Query("SELECT * FROM NOTE /*where note.removeTime==null*/")
    @Query("SELECT * FROM NOTE")
    suspend  fun getAll(): List<Note>

    @Query("Select * from NOTE where id=:idNote")
    suspend fun getById(idNote: Long): Note

    @Update
    suspend   fun update(note: NoteEntity)

    @Delete
    suspend   fun deleteNote(note: NoteEntity)

/*  @Query("UPDATE NOTE SET REMOVETIME = @System.currentTimeMillis() where id = idNote")
   suspend   fun logicDelete(idNote: Long)*/
   //    fun getAll(): LiveData<List<Note>>

/*  @Query("Select * from NOTE where id=:idNote")
   fun getById(idNote:Long): List<NoteEntity>*/


}