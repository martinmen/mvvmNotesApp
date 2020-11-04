package ar.com.unlam.notesapp.domain.model

import androidx.room.ColumnInfo

data class Note(
    var id: Long = 0,
    var titulo: String,
    var comentario: String,
    val creationTime: Long = System.currentTimeMillis()
    //val modifidedTime: Long?,
   // val removeTime: Long?
) {

}