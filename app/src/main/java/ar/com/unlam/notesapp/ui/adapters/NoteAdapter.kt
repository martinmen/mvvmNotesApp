package ar.com.unlam.notesapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentViewHolder
import ar.com.unlam.notesapp.R
import ar.com.unlam.notesapp.databinding.NoteItemBinding
import ar.com.unlam.notesapp.domain.model.Note
import ar.com.unlam.notesapp.ui.viewModels.NoteViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.note_item.view.*

//class NoteAdapter (val noteList:List<Note>): RecyclerView.Adapter<NoteAdapter.NoteHolder>(){
class NoteAdapter(val onItemDetailViewClick: (note: Note) -> Unit) :
    RecyclerView.Adapter<NoteAdapter.NoteHolder>() {
    //lateinit var NoteitemRemoved : Note
    var titleNoteDeleted = ""
    var idNoteDeleted: Long = 0
    private val noteList = mutableListOf<Note>()

    class NoteHolder(private val binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun binNote(note: Note) {
            binding.etNombreNote.text = note.titulo
            binding.etCommentNote.text = note.comentario
            binding.etLocationNote.text = note.provincia
            Picasso.get()
                .load(
                    note.imagen
                )
                .into(binding.imgItemResult)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteHolder(binding)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.binNote(noteList[position])
        holder.itemView.setOnClickListener {
            onItemDetailViewClick(noteList[position])
        }
    }

    fun submitList(it: List<Note>) {
        noteList.clear()
        noteList.addAll(it)
    }

    fun deleteItem(position: Int, viewHolder: RecyclerView.ViewHolder): Long {
        noteList.removeAt(position)
        notifyItemRemoved(position)
        if (position == 0) {

            return noteList[position].id
        } else {
            return (noteList[position-1].id)
        }
    }

    fun returnNoteDelete(): String {
        return titleNoteDeleted
    }


    /*       holder.itemView.setOnClickListener {
               onItemDetailViewClick(noteList[position])
           }*/
}
