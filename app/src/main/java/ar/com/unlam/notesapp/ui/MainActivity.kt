package ar.com.unlam.notesapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import ar.com.unlam.notesapp.DetailNoteActivity
import ar.com.unlam.notesapp.R
import ar.com.unlam.notesapp.databinding.ActivityMainBinding
import ar.com.unlam.notesapp.domain.model.Note

class MainActivity : AppCompatActivity() {

    private val viewModel: NoteViewModel by viewModels { NoteViewModelFactory(applicationContext) }

    private lateinit var adapter: NoteAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = NoteAdapter { note -> toOnItemViewClick(note) }
        with(binding.rvNoteList) {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            this.adapter = this@MainActivity.adapter
        }
        //Ir a AddNote
        binding.buttonGoAddNote.setOnClickListener {
            val intent: Intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }

        viewModel.getNotes()
        viewModel.notesListLiveData.observe(this, Observer {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        })
    }

    //Ir al detalle de la nota seleccionada
    private fun toOnItemViewClick(note: Note) {
        val intent = Intent(this, DetailNoteActivity::class.java)
        intent.putExtra("nombreNota", note.nombre)
        startActivity(intent)
    }
}
