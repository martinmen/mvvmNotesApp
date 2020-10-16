package ar.com.unlam.notesapp.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ar.com.unlam.notesapp.domain.model.Note

@Dao
interface NotesDao {

    @Insert
    fun addNote(entity: NoteEntity)

    @Query("SELECT * FROM NOTE")
    fun getAll(): List<Note>
    //    fun getAll(): LiveData<List<Note>>


    @Query("Select * from NOTE where id=:idNote")
    fun getById(idNote:Int): List<NoteEntity>


    @Query("Select * from NOTE where nombre=:nombreNote")
    fun getById(nombreNote:String): Note

   // fun update(note:NoteEntity)
}