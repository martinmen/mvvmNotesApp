package ar.com.unlam.notesapp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import ar.com.unlam.notesapp.data.room.NoteDataBase
import ar.com.unlam.notesapp.data.room.RoomNoteRepository

class NoteViewModelFactory(
    private val  applicationContext: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val database = Room.databaseBuilder(
            applicationContext,
            NoteDataBase::class.java,
            "notes-db"
        )
            .allowMainThreadQueries()
            .build()

   val dao = database.noteDao()

        return NoteViewModel(
            RoomNoteRepository(dao)
     ) as T
    }
}