package ar.com.unlam.notesapp.utils

import java.text.SimpleDateFormat
import java.util.*

fun Long.convertToTime(): String{
    val date = Date(this)
    val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
    return format.format(date)
}