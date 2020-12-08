package ar.com.unlam.notesapp.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.unlam.notesapp.data.room.repositories.NoteRepository
import ar.com.unlam.notesapp.domain.model.Note
import kotlinx.coroutines.launch


class NoteViewModel(private val noteRepository: NoteRepository) : ViewModel() {

    val notesListLiveData = MutableLiveData<List<Note>>()
    val noteLiveData = MutableLiveData<Note>()
    val status = MutableLiveData<Status>()

    enum class Status {
        SUCCES,
        ERROR,
    }

    init {
        viewModelScope.launch {
            try {
                notesListLiveData.value = noteRepository.getMyNotes()
                status.value = Status.SUCCES
            } catch (e: Exception) {
                status.value = Status.ERROR
            }
        }
    }

    fun getNotes() {
        viewModelScope.launch {
            try {
                notesListLiveData.value = noteRepository.getMyNotes()
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
            } catch (e: Exception) {
                status.value = Status.ERROR
            }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            try {
                noteRepository.deleteNote(note)
                status.value = Status.SUCCES
            } catch (e: Exception) {
                status.value = Status.ERROR
            }
        }
    }

    fun undoDeleteNote(note: Note) {
        viewModelScope.launch {
            try {
                noteRepository.undoDeleteNote(note)
                status.value = Status.SUCCES
            } catch (e: Exception) {
                status.value = Status.ERROR
            }
        }
    }
}