package ar.com.unlam.notesapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import ar.com.unlam.notesapp.R
import ar.com.unlam.notesapp.databinding.ActivityAddNoteBinding
import ar.com.unlam.notesapp.domain.model.Note
import ar.com.unlam.notesapp.ui.viewModels.AddNoteViewModel
import ar.com.unlam.notesapp.ui.viewModels.GeneralNoteViewModelFactory

class AddNoteActivity : AppCompatActivity() {
private val nameActivity = "AddNoteActivity"
    private val viewModel: AddNoteViewModel by viewModels { GeneralNoteViewModelFactory(applicationContext, nameActivity) }
    private lateinit var binding: ActivityAddNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var idNota: Long? = null

        if (intent.hasExtra("idNote")) {
            var idNote = intent.getLongExtra("idNote", 0)
            viewModel.getNoteById(idNote)
            viewModel.noteLiveData.observe(this, Observer {
                binding.editTextTituloNota.setText(it.titulo)
                binding.editTextComentarioNota.setText(it.comentario)
                idNota = it.id
            })
        }

        binding.buttonAddNote.setOnClickListener {

            if (binding.editTextTituloNota.text.isNullOrEmpty()) {
                Toast.makeText(this, getString(R.string.name_must_completed), Toast.LENGTH_LONG)
                    .show()
            } else {
                val note = Note(
                    titulo = binding.editTextTituloNota.text.toString(),
                    comentario = binding.editTextComentarioNota.text.toString()
                )
                if (idNota != null) {
                    val note = Note(
                        idNota!!,
                        titulo = binding.editTextTituloNota.text.toString(),
                        comentario = binding.editTextComentarioNota.text.toString()
                    )
                    viewModel.updateNote(note)
                    //viewModel.getNoteById(idNota!!)
                     this@AddNoteActivity.finish()
                } else {
                    viewModel.addNote(note)
                    this@AddNoteActivity.finish()
                }
            }
        }
    }


}
