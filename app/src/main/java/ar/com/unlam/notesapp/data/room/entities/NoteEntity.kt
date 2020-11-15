package ar.com.unlam.notesapp.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "note")
class NoteEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "titulo")
    var titulo: String = "",
    @ColumnInfo(name = "comentario")
    var comentario: String = "",
    @ColumnInfo(name = "provincia")
    var provincia: String = "",
    @ColumnInfo(name = "municipio")
    var municipio: String = "",
    @ColumnInfo(name = "creationTime")
    val creationTime: Long?,
    @ColumnInfo(name = "modifidedTime")
    val modifidedTime: Long?,
    @ColumnInfo(name = "removeTime")
    val removeTime: Long?
)