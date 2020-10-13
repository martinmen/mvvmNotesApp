package ar.com.unlam.notesapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ar.com.unlam.notesapp.domain.Note

@Dao
interface NotesDao {

    @Insert
    fun addNote(entity: NoteEntity)

    @Query("SELECT * FROM NOTE")
    fun getAll(): List<Note>
}