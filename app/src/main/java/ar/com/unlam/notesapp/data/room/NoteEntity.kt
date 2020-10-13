package ar.com.unlam.notesapp.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName ="note")
class NoteEntity (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id:Long = 0,

    @ColumnInfo(name = "nombre")
    var nombre:String="",

    @ColumnInfo(name = "comentario")
    var comentario:String=""
)