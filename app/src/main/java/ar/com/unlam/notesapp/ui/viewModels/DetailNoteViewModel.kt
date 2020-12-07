package ar.com.unlam.notesapp.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.unlam.notesapp.data.room.repositories.NoteRepository
import ar.com.unlam.notesapp.domain.model.Note
import kotlinx.coroutines.launch

class DetailNoteViewModel(private val noteRepository: NoteRepository) : ViewModel() {

    val noteLiveData = MutableLiveData<Note>()
    val status = MutableLiveData<Status>()

    enum class Status {
        SUCCES,
        ERROR,
    }
    fun getNoteById(idNote: Long) {
        try {
            viewModelScope.launch { noteLiveData.value = noteRepository.getNoteById(idNote) }
            status.value = Status.SUCCES
        }catch (e:Exception){
            status.value = Status.ERROR
        }
    }

    fun deleteNote(note: Note) {
        try {
            viewModelScope.launch { noteRepository.deleteNote(note)
                status.value = Status.SUCCES
            }

        }catch (e:Exception){
            status.value = Status.ERROR
        }

    }

}