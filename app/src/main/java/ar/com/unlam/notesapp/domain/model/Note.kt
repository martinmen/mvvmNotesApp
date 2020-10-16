package ar.com.unlam.notesapp.domain.model

data class Note(
    val id: Long = 0,
    var titulo: String,
    var comentario: String,
    val creationTime: Long = System.currentTimeMillis()
) {

}