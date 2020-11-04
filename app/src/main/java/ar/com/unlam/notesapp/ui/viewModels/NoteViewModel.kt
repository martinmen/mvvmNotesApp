package ar.com.unlam.notesapp.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.unlam.notesapp.data.room.RoomNoteRepository
import ar.com.unlam.notesapp.domain.model.Note
import kotlinx.coroutines.launch

class NoteViewModel(private val noteRepository: RoomNoteRepository) :BaseNoteViewModel(noteRepository) {
    val displayNotesListLiveData = MutableLiveData<List<Note>>()
    val notesListLiveData = MutableLiveData<List<Note>>()

    init {viewModelScope.launch {notesListLiveData.value = noteRepository.getMyNotes()}}

    fun getNotes() {viewModelScope.launch {notesListLiveData.value = noteRepository.getMyNotes()}}

}