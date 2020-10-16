package ar.com.unlam.notesapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    version = 3,
    entities = [NoteEntity::class]
)
abstract class NoteDataBase : RoomDatabase() {

    abstract fun noteDao(): NotesDao
}