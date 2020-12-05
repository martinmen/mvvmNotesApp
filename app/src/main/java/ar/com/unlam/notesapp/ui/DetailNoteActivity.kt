package ar.com.unlam.notesapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import ar.com.unlam.notesapp.R
import ar.com.unlam.notesapp.databinding.ActivityDetailNoteBinding
import ar.com.unlam.notesapp.ui.viewModels.DetailNoteViewModel
import ar.com.unlam.notesapp.utils.convertToTime
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_note.*
import org.koin.android.viewmodel.ext.android.viewModel

class DetailNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailNoteBinding
    var idNoteEditable: Long = 0
    private val viewModel: DetailNoteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_note)

        binding = ActivityDetailNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idNote = intent.getLongExtra(NOTE_ID, 0)
        idNoteEditable = idNote


        setObservers()
        viewModel.getNoteById(idNoteEditable)
    }


    companion object{
        const val NOTE_ID = "idNote"
    }

    private fun setObservers() {
        viewModel.noteLiveData.observe(this, Observer {
            binding.texViewTituloNote.text = it.titulo
            binding.texViewComentarioNote.text = it.comentario
            Picasso.get()
                .load(it.imagen)
                .into(binding.imageViewDetail)
            if (it.provincia != "") {
                binding.texViewProvincia.text =
                    it.provincia + getString(R.string.commasSeparator) + it.municipio + getString(R.string.creationAt) + it.creationTime.convertToTime()
            }

            var idNoteToEdit = it.id
        })
    }

    override fun onStart() {
        viewModel.getNoteById(idNoteEditable)
        super.onStart()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.note_detail_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_edit_note -> {

                val intent = Intent(this, AddNoteActivity::class.java)
                intent.putExtra(NOTE_ID, idNoteEditable)
                startActivity(intent)
            }
            R.id.item_delete_note -> {
                viewModel.noteLiveData.removeObservers(this)
                viewModel.deleteNote(viewModel.noteLiveData.value!!)
                val intent = Intent(this, NoteActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }



}