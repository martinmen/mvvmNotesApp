package ar.com.unlam.notesapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ar.com.unlam.notesapp.data.room.entities.NoteEntity


@Database(
    version = 3,
    entities = [NoteEntity::class]
)
abstract class NoteDataBase : RoomDatabase() {

    abstract fun noteDao(): NotesDao
}