package ar.com.unlam.notesapp.data.exceptions

import java.lang.RuntimeException

class RepositoryException (message:String,cause: Throwable): RuntimeException(message,cause) {
}