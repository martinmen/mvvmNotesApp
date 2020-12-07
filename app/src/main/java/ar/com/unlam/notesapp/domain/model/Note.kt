package ar.com.unlam.notesapp.domain.model

import android.graphics.Bitmap
import android.net.Uri
import androidx.room.ColumnInfo

data class Note(
    var id: Long = 0,
    var titulo: String,
    var comentario: String?,
    var provincia: String?,
    var municipio: String?,
    val imagen: String?,
    val creationTime: Long = System.currentTimeMillis(),
    val modifidedTime: Long? = 0,
    val removeTime: Long? = null
) {

}