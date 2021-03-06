package ar.com.unlam.notesapp.data.room.entities

import android.graphics.Bitmap
import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ar.com.unlam.notesapp.domain.model.Note


@Entity(tableName = "note")
class NoteEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "titulo")
    var titulo: String = "",
    @ColumnInfo(name = "comentario")
    var comentario: String? = "",
    @ColumnInfo(name = "provincia")
    var provincia: String? = "",
    @ColumnInfo(name = "municipio")
    var municipio: String? = "",
    @ColumnInfo(name = "imagen")
    var imagen : String?,
    @ColumnInfo(name = "creationTime")
    val creationTime: Long?,
    @ColumnInfo(name = "modifidedTime")
    val modifidedTime: Long?,
    @ColumnInfo(name = "removeTime")
    val removeTime: Long?
)

