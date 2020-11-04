package ar.com.unlam.notesapp.ui.viewModels

import androidx.lifecycle.viewModelScope
import ar.com.unlam.notesapp.data.room.RoomNoteRepository
import ar.com.unlam.notesapp.domain.model.Note
import kotlinx.coroutines.launch

class AddNoteViewModel (private val noteRepository: RoomNoteRepository) :BaseNoteViewModel(noteRepository) {

    fun addNote(note: Note) {viewModelScope.launch { noteRepository.addNote(Note(titulo = note.titulo, comentario = note.comentario))}}

    fun updateNote(note: Note) {viewModelScope.launch {noteRepository.updateNote(note)}}
}