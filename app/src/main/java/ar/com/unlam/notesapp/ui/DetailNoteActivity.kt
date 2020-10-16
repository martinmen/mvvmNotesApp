package ar.com.unlam.notesapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import ar.com.unlam.notesapp.R
import ar.com.unlam.notesapp.databinding.ActivityDetailNoteBinding

class DetailNoteActivity : AppCompatActivity() {


    private lateinit var binding: ActivityDetailNoteBinding
    var idNoteEditable: Long = 0
    private val viewModel: NoteViewModel by viewModels { NoteViewModelFactory(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_note)

        binding = ActivityDetailNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idNote = intent.getLongExtra("idNote", 0)
        idNoteEditable = idNote
        viewModel.getNoteById(idNote!!)
        viewModel.NoteLiveData.observe(this, Observer {
            binding.texViewTituloNote.text = it.titulo
            binding.texViewComentarioNote.text = it.comentario
            var idNoteToEdit = it.id
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.note_detail_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_edit_note -> {

                val intent = Intent(this, AddNoteActivity::class.java)
                intent.putExtra("idNote", idNoteEditable)
                startActivity(intent)

            }

            R.id.item_delete_note -> {
                viewModel.NoteLiveData.removeObservers(this)
                viewModel.deleteNote(viewModel.NoteLiveData.value!!)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

}