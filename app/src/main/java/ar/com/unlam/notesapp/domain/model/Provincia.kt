package ar.com.unlam.notesapp.domain.model

import com.google.gson.annotations.SerializedName

data class Provincia (

	@SerializedName("id") val id : Int,
	@SerializedName("nombre") val nombre : String
)