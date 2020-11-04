package ar.com.unlam.notesapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.com.unlam.notesapp.R
import ar.com.unlam.notesapp.databinding.ActivityMainBinding
import ar.com.unlam.notesapp.domain.model.Note
import ar.com.unlam.notesapp.ui.adapters.NoteAdapter
import ar.com.unlam.notesapp.ui.viewModels.NoteViewModel
import ar.com.unlam.notesapp.ui.viewModels.GeneralNoteViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class NoteActivity : AppCompatActivity() {
    val nameActivity = "NoteActivity"
    private val viewModel  by viewModels <NoteViewModel> { GeneralNoteViewModelFactory(applicationContext,nameActivity) }
    private lateinit var adapter: NoteAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = NoteAdapter { note -> toOnItemViewClick(note) }

        with(binding.rvNoteList) {

            // layoutManager = GridLayoutManager(applicationContext,2,LinearLayoutManager.VERTICAL,false) // Para implementar en con otro estilo
            layoutManager = LinearLayoutManager(this@NoteActivity, LinearLayoutManager.VERTICAL, false)
            this.adapter = this@NoteActivity.adapter
        }


        //Ir a AddNote
        binding.buttonGoAddNote.setOnClickListener {
            val intent: Intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }
        //Swip To delete
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
        {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                    return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, position: Int) {
                    adapter.deleteItem(viewHolder.adapterPosition,viewHolder)
          //      viewModel.getNoteById(adapter.idNoteDeleted)
        //        var noteDeleted : Note? = viewModel.noteLiveData.value
      //          noteDeleted?.let { viewModel.deleteNote(it) }
             }
        }
        viewModel.noteLiveData.observe(this, Observer {

        })
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(rv_note_list)

        viewModel.notesListLiveData.observe(this, Observer {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        })
    }

    override fun onStart() {
        viewModel.getNotes()
        super.onStart()
    }

    // filtro de searchView FALTA IMPLEMENTAR
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu_search, menu)
        val searhItem = menu!!.findItem(R.id.menu_search)
        if (searhItem != null) {
            val searView = searhItem.actionView as SearchView

            searView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
/*                    if (newText!!.isNotEmpty()) {

                        val search = newText.toLowerCase(Locale.getDefault())
                        viewModel.notesListLiveData.value?.forEach {
                            if (it.titulo.toLowerCase(Locale.getDefault()).contains(search)) {
                                // displayList.displayNotesListLiveData.value = viewModel.notesListLiveData.value?.filter { it.nombre == search  }
                            }

                        }
                        adapter.notifyDataSetChanged()
                    } else {

                    }*/
                    return true
                }
            })
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return super.onOptionsItemSelected(item)
    }

    //Ir al detalle de la nota seleccionada
    private fun toOnItemViewClick(note: Note) {
        val intent = Intent(this, DetailNoteActivity::class.java)
        intent.putExtra("idNote", note.id)
        startActivity(intent)
    }





}
