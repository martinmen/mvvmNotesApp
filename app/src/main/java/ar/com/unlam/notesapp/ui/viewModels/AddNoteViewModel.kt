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
    val status = MutableLiveData<Status>()
    val routingActivity = MutableLiveData<Status>()

    enum class Status {
        SUCCES,
        ERROR,
        VALID,
        NOT_VALID,
        LOCATION_SUCCED,
        LOCATION_ERROR,
        TO_ADD_NOTE,
        TO_UPDATE_NOTE
    }

    fun verifyRequeried(note: Note): Boolean {
        if (note.titulo=="") {
            status.value= Status.NOT_VALID
            return false
        }
        else {
            status.value = Status.VALID
            return true
        }

    }

    fun addNote(note: Note) {
        viewModelScope.launch {
            try {
                noteRepository.addNote(
                    Note(
                        titulo = note.titulo,
                        comentario = note.comentario,
                        provincia = note.provincia,
                        municipio = note.municipio,
                        imagen = note.imagen
                    )
                )
                status.value = Status.SUCCES
            } catch (e: Exception) {
                status.value = Status.ERROR
            }
        }
    }

    fun getNoteById(idNote: Long) {
        viewModelScope.launch {
            try {
                noteLiveData.value = noteRepository.getNoteById(idNote)
                status.value = Status.SUCCES
            } catch (ignored: Exception) {
                status.value = Status.ERROR
            }
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            try {
                noteRepository.updateNote(note)
                status.value = Status.SUCCES
            } catch (ignored: Exception) {
                status.value = Status.ERROR
            }
        }
    }

    fun getLocation(lat: String, lon: String) {
        viewModelScope.launch {

            try {
                locationRepository.getLocation(
                    lat,
                    lon,
                    { locationLiveData.postValue(it) })
                status.value = Status.LOCATION_SUCCED
            } catch (ignored: Exception) {
                status.value = Status.LOCATION_ERROR
            }

        }
    }

    fun checkAddOrUpdate(idNote: Long?, note: Note?) {
        if (idNote != null) {
            note!!.id = idNote
            routingActivity.value = Status.TO_UPDATE_NOTE
            try {
                updateNote(note)
            } catch (ignored: Exception) {
                status.value = Status.ERROR
            }

        } else {
            routingActivity.value = Status.TO_ADD_NOTE
            try {
               addNote(note!!)
            } catch (ignored: Exception) {
                status.value = Status.ERROR
            }

        }
    }
}