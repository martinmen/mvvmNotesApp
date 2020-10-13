package ar.com.unlam.notesapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ar.com.unlam.notesapp.R
import ar.com.unlam.notesapp.databinding.ActivityMainBinding
import ar.com.unlam.notesapp.databinding.NoteItemBinding
import ar.com.unlam.notesapp.domain.Note
import kotlinx.android.synthetic.main.note_item.view.*

//class NoteAdapter (val noteList:List<Note>): RecyclerView.Adapter<NoteAdapter.NoteHolder>(){

    class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteHolder>(){

        private val noteList = mutableListOf<Note>()

    class NoteHolder(private val binding: NoteItemBinding): RecyclerView.ViewHolder(binding.root){

        fun binNote(note:Note){
            binding.etNombreNote.text = note.nombre
            binding.etCommentNote.text = note.comentario
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NoteHolder(binding)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
            holder.binNote(noteList[position])
        }

        fun submitList(it: List<Note>) {
            noteList.clear()
            noteList.addAll(it)
        }


        /*       holder.itemView.setOnClickListener {
                   onItemDetailViewClick(noteList[position])
               }*/
    }
