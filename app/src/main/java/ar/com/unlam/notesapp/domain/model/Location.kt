package ar.com.unlam.notesapp.domain.model

import com.google.gson.annotations.SerializedName


data class Location(

	@SerializedName("parametros") val parametros: Parametros,
	@SerializedName("ubicacion") val ubicacion: Ubicacion
)