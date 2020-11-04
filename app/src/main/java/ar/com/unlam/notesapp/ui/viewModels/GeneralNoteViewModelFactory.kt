package ar.com.unlam.notesapp.ui.viewModels

import android.content.Context
import android.content.pm.PackageManager
import android.widget.Switch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import ar.com.unlam.notesapp.data.room.NoteDataBase
import ar.com.unlam.notesapp.data.room.NotesDao
import ar.com.unlam.notesapp.data.room.RoomNoteRepository
import kotlin.contracts.Returns

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
            "AddNoteActivity" -> return AddNoteViewModel(RoomNoteRepository(dao))

            "DetailNoteActivity" -> return DetailNoteViewModel(RoomNoteRepository(dao))

            "NoteActivity" -> return NoteViewModel(RoomNoteRepository(dao))

            else ->  return NoteViewModel(RoomNoteRepository(dao))
        }
    }

}