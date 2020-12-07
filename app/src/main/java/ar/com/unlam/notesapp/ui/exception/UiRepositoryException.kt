package ar.com.unlam.notesapp.ui.exception

import java.lang.RuntimeException

class UiRepositoryException (message:String,cause: Throwable): RuntimeException(message,cause) {
}