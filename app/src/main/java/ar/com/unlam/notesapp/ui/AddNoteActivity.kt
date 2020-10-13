package ar.com.unlam.notesapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ar.com.unlam.notesapp.R
import ar.com.unlam.notesapp.databinding.ActivityAddNoteBinding
import ar.com.unlam.notesapp.databinding.ActivityMainBinding
import ar.com.unlam.notesapp.domain.Note
import kotlinx.android.synthetic.main.activity_add_note.*

class AddNoteActivity : AppCompatActivity() {

    private val viewModel:NoteViewModel by viewModels { NoteViewModelFactory(applicationContext) }
private val successAdd : Boolean = false
private lateinit var binding : ActivityAddNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

      binding.buttonAddNote.setOnClickListener{
          val note = Note(binding.editTextNombreNota.text.toString(),binding.editTextComentarioNota.text.toString())
          viewModel.addNote(note)
          val intent: Intent = Intent(this,MainActivity::class.java)
          startActivity(intent)

      }
  }
}