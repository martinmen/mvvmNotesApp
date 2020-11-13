package ar.com.unlam.notesapp.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.unlam.notesapp.data.room.repositories.NoteRepository
import ar.com.unlam.notesapp.domain.model.Note
import kotlinx.coroutines.launch

//class DetailNoteViewModel (private val noteRepository: RoomNoteRepositoryImp) :BaseNoteViewModel(noteRepository) { implementacion sin Koin
class DetailNoteViewModel(private val noteRepository: NoteRepository) : ViewModel() {

     val noteLiveData = MutableLiveData<Note>()

     fun getNoteById(idNote: Long) {
         viewModelScope.launch {noteLiveData.value = noteRepository.getNoteById(idNote)}
        }

     fun deleteNote(note: Note) {
        viewModelScope.launch { noteRepository.deleteNote(note) }
        }

}