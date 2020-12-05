package ar.com.unlam.notesapp.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.unlam.notesapp.data.retrofit.repositories.LocationRepository
import ar.com.unlam.notesapp.data.room.repositories.NoteRepository
import ar.com.unlam.notesapp.domain.model.Location
import ar.com.unlam.notesapp.domain.model.Note
import kotlinx.coroutines.launch

class AddNoteViewModel(
    private val noteRepository: NoteRepository,
    private val locationRepository: LocationRepository
) : ViewModel() {
    val noteLiveData = MutableLiveData<Note>()
    val locationLiveData = MutableLiveData<Location>()

    fun addNote(note: Note) {
        viewModelScope.launch {
            noteRepository.addNote(
                Note(
                    titulo = note.titulo,
                    comentario = note.comentario,
                    provincia = note.provincia,
                    municipio = note.municipio,
                    imagen = note.imagen
                )
            )
        }
    }

    fun getNoteById(idNote: Long) {
        viewModelScope.launch { noteLiveData.value = noteRepository.getNoteById(idNote) }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch { noteRepository.updateNote(note) }
    }

     fun getLocation(lat: String, lon: String,) {
     viewModelScope.launch {   locationRepository.getLocation(lat, lon,{ locationLiveData.postValue(it) }) }
    }

    fun checkAddOrUpdate(idNote: Long?, note: Note?) {
        if (idNote != null) {
            note!!.id = idNote
            updateNote(note!!)
        } else {
            addNote(note!!)
        }

    }
}