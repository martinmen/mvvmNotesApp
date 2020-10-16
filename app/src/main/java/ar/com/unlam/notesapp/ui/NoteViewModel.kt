package ar.com.unlam.notesapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ar.com.unlam.notesapp.data.room.RoomNoteRepository
import ar.com.unlam.notesapp.domain.model.Note

class NoteViewModel(
    private val noteRepository: RoomNoteRepository
) : ViewModel() {

    val displayNotesListLiveData = MutableLiveData<List<Note>>()
    val notesListLiveData = MutableLiveData<List<Note>>()
    val NoteLiveData = MutableLiveData<Note>()

    init {
        notesListLiveData.value = noteRepository.getMyNotes()
    }

    fun getNotes() {
        notesListLiveData.value = noteRepository.getMyNotes()
    }

    fun getNoteById(idNote: Long) {
        NoteLiveData.value = noteRepository.getNoteById(idNote)
    }

    fun addNote(note: Note) {
        noteRepository.addNote(Note(titulo = note.titulo, comentario = note.comentario))
        notesListLiveData.value = noteRepository.getMyNotes()
    }

    fun updateNote(note: Note) {
        noteRepository.updateNote(note)
    }

    fun deleteNote(note: Note) {
        noteRepository.deleteNote(note)
    }


}