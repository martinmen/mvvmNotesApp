package ar.com.unlam.notesapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import ar.com.unlam.notesapp.databinding.ActivityAddNoteBinding
import ar.com.unlam.notesapp.domain.model.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddNoteActivity : AppCompatActivity() {

    private val viewModel:NoteViewModel by viewModels { NoteViewModelFactory(applicationContext) }
private val successAdd : Boolean = false
private lateinit var binding : ActivityAddNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var nombreNota: String? = null
        if (intent.hasExtra("nombreNota")){
            val nombreNote = intent.getStringExtra("nombreNota")
            viewModel.getNoteById(nombreNote!!)
            viewModel.NoteLiveData.observe(this, Observer {
                binding.editTextNombreNota.setText(it.nombre)
                binding.editTextComentarioNota.setText(it.comentario)
                nombreNota = it.nombre
            })
        }


        //Consultar por el Model y data class ya que tengo confucion a la hora de tratar el ID del objeto a modificar o borrar.
        binding.buttonAddNote.setOnClickListener{
        if (nombreNota !=null){
            CoroutineScope(Dispatchers.IO).launch {
                var note = Note(binding.editTextNombreNota.text.toString(),binding.editTextComentarioNota.text.toString())
                viewModel.updateNote(note)
              val intent:Intent = Intent(this@AddNoteActivity,MainActivity::class.java)
                startActivity(intent)
            }
        }else{

                val note = Note(binding.editTextNombreNota.text.toString(),binding.editTextComentarioNota.text.toString())
                viewModel.addNote(note)
                val intent: Intent = Intent(this,MainActivity::class.java)
                startActivity(intent)

            }
        }


  }
}