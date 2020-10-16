package ar.com.unlam.notesapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ar.com.unlam.notesapp.data.room.RoomNoteRepository
import ar.com.unlam.notesapp.domain.model.Note

class NoteViewModel(
    private val noteRepository: RoomNoteRepository
) : ViewModel() {

val notesListLiveData = MutableLiveData<List<Note>>()
    val NoteLiveData = MutableLiveData<Note>()

    init {
        notesListLiveData.value = noteRepository.getMyNotes()
    }
    fun getNotes(){
        notesListLiveData.value = noteRepository.getMyNotes()
    }

    fun getNoteById(nombre:String){
        NoteLiveData.value = noteRepository.getNoteById(nombre)
    }

    fun addNote(note : Note){
        noteRepository.addNote(Note(note.nombre,note.comentario))
        notesListLiveData.value = noteRepository.getMyNotes()
    }
}