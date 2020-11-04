package ar.com.unlam.notesapp.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.unlam.notesapp.data.room.RoomNoteRepository
import ar.com.unlam.notesapp.domain.model.Note
import kotlinx.coroutines.launch

abstract class BaseNoteViewModel(private val noteRepository: RoomNoteRepository) : ViewModel(){
    val noteLiveData = MutableLiveData<Note>()

    fun getNoteById(idNote: Long) {viewModelScope.launch {noteLiveData.value = noteRepository.getNoteById(idNote)}}

    fun deleteNote(note: Note) {viewModelScope.launch {noteRepository.deleteNote(note)}}

}