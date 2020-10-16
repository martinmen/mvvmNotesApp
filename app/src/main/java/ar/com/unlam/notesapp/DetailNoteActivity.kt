package ar.com.unlam.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import ar.com.unlam.notesapp.databinding.ActivityDetailNoteBinding
import ar.com.unlam.notesapp.databinding.ActivityMainBinding
import ar.com.unlam.notesapp.ui.NoteAdapter
import ar.com.unlam.notesapp.ui.NoteViewModel
import ar.com.unlam.notesapp.ui.NoteViewModelFactory

class DetailNoteActivity : AppCompatActivity() {


    private lateinit var binding: ActivityDetailNoteBinding

    private val viewModel: NoteViewModel by viewModels { NoteViewModelFactory(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_note)

        binding = ActivityDetailNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nombreNote = intent.getStringExtra("nombreNota")
        viewModel.getNoteById(nombreNote!!)
        viewModel.NoteLiveData.observe(this, Observer {
            binding.texViewNombreNote.text = it.nombre
            binding.texViewComentarioNote.text = it.comentario
        })

    }
}