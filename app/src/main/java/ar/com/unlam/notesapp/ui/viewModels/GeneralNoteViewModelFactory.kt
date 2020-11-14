package ar.com.unlam.notesapp.ui.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import ar.com.unlam.notesapp.data.room.NoteDataBase
import ar.com.unlam.notesapp.data.room.NotesDao
import ar.com.unlam.notesapp.data.room.repositories.RoomNoteRepositoryImpl

class GeneralNoteViewModelFactory(private val applicationContext: Context, activityName:String) : ViewModelProvider.Factory {
private var globalName = activityName
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val database = Room.databaseBuilder(
            applicationContext,
            NoteDataBase::class.java,
            "notes-db"
        )
            //.allowMainThreadQueries() NO HACER. PAra esto estan las CORUTINES
            .build()
        val dao = database.noteDao()

     //   return NoteViewModel(RoomNoteRepository(dao)) as T
        return evaluateFactoryReturn(globalName,dao) as T
    }

    fun evaluateFactoryReturn(activityName: String,dao: NotesDao): ViewModel {
        return when (activityName) {
            //"AddNoteActivity" -> return AddNoteViewModel(RoomNoteRepositoryImpl(dao))

            "DetailNoteActivity" -> return DetailNoteViewModel(RoomNoteRepositoryImpl(dao))

            "NoteActivity" -> return NoteViewModel(RoomNoteRepositoryImpl(dao))

            else ->  return NoteViewModel(RoomNoteRepositoryImpl(dao))
        }
    }

}