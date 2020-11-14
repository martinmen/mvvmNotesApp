package ar.com.unlam.notesapp.domain.model

import com.google.gson.annotations.SerializedName

data class Departamento(

	@SerializedName("id") val id: Int,
	@SerializedName("nombre") val nombre: String
)