package ar.com.unlam.notesapp

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.room.Room
import ar.com.unlam.notesapp.data.retrofit.RetrofitLocationService
import ar.com.unlam.notesapp.data.retrofit.RetrofitLocationServiceImpl
import ar.com.unlam.notesapp.data.retrofit.repositories.LocationRepository
import ar.com.unlam.notesapp.data.retrofit.repositories.LocationRepositoryImpl
import ar.com.unlam.notesapp.data.room.repositories.NoteRepository
import ar.com.unlam.notesapp.data.room.NoteDataBase
import ar.com.unlam.notesapp.data.room.NotesDao
import ar.com.unlam.notesapp.data.room.repositories.RoomNoteRepositoryImpl
import ar.com.unlam.notesapp.domain.model.Note
import ar.com.unlam.notesapp.ui.DetailNoteActivity
import ar.com.unlam.notesapp.ui.viewModels.AddNoteViewModel
import ar.com.unlam.notesapp.ui.viewModels.DetailNoteViewModel
import ar.com.unlam.notesapp.ui.viewModels.NoteViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class NotesApp : Application() {

    val appModule = module {
        single<NotesDao> { dataBaseProvider(get()).noteDao() }
        single<NoteRepository> { RoomNoteRepositoryImpl(get()) }

        single<LocationRepository> { LocationRepositoryImpl(get()) }
        single<RetrofitLocationService> { RetrofitLocationServiceImpl() }

        /*    single { NoteAdapter { note -> toOnItemViewClick(get()) } }*/
        viewModel { NoteViewModel(get()) }
        viewModel { AddNoteViewModel(get(), get()) }
        viewModel { DetailNoteViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {

            androidLogger()
            androidContext(this@NotesApp)
            koin.loadModules(listOf(appModule))
            koin.createRootScope()
            // modules(appModule) de esta manera me lanza un error.
        }
    }

    fun dataBaseProvider(context: Context): NoteDataBase {

        return Room.databaseBuilder(context, NoteDataBase::class.java, "notes-db2").build()
    }
}
/*  private fun toOnItemViewClick(note: Note) {
    val intent = Intent(this, DetailNoteActivity::class.java)
    intent.putExtra("idNote", note.id)
    startActivity(intent)
}

fun database(context: Context):NotesDao{
    val database = Room.databaseBuilder(
        applicationContext,
        NoteDataBase::class.java,
        "notes-db"
    )
        //.allowMainThreadQueries() NO HACER. PAra esto estan las CORUTINES
        .build()
    val dao = database.noteDao()
    return dao
}*/

