package ar.com.unlam.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import ar.com.unlam.notesapp.databinding.ActivityDetailNoteBinding
import ar.com.unlam.notesapp.databinding.ActivityMainBinding
import ar.com.unlam.notesapp.ui.AddNoteActivity
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
       menuInflater.inflate(R.menu.note_detail_menu,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item_edit_note ->{
                val intent = Intent(this,AddNoteActivity::class.java)
                intent.putExtra("nombreNota", binding.texViewNombreNote.text)
               // intent.putExtra("comentarioNota", binding.texViewComentarioNote.text)
                startActivity(intent)

            }

            R.id.item_delete_note ->{}
        }

        return super.onOptionsItemSelected(item)
    }

}