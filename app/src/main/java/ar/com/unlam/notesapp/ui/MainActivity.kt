package ar.com.unlam.notesapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import ar.com.unlam.notesapp.R
import ar.com.unlam.notesapp.databinding.ActivityMainBinding
import ar.com.unlam.notesapp.domain.Note
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel:NoteViewModel by viewModels { NoteViewModelFactory(applicationContext) }

    private lateinit var adapter: NoteAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = NoteAdapter()
        with(binding.rvNoteList){
            layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.VERTICAL,false)
            this.adapter = this@MainActivity.adapter
        }


        //click Listener
        binding.buttonGoAddNote.setOnClickListener{
            val intent: Intent = Intent(this,AddNoteActivity::class.java)
          startActivity(intent)
        }

        viewModel.getNotes()
        viewModel.notesListLiveData.observe(this, Observer {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        })
        }
    }
