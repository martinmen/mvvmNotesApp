package ar.com.unlam.notesapp.ui.viewModels

import androidx.lifecycle.ViewModel
import ar.com.unlam.notesapp.data.room.repositories.NoteRepository
//Tenia errores cunado implementaba esta clase. Lo tengo pendiente de revisar
abstract class BaseNoteViewModel(private val noteRepository: NoteRepository) : ViewModel(){

   // open val noteLiveData = MutableLiveData<Note>()
  //  open fun getNoteById(idNote: Long) {viewModelScope.launch {noteLiveData.value = noteRepository.getNoteById(idNote)}}
  //  fun deleteNote(note: Note) {viewModelScope.launch {noteRepository.deleteNote(note)}}

}