package ar.com.unlam.notesapp.domain.model

import com.google.gson.annotations.SerializedName


data class Parametros(

	@SerializedName("lat") val lat: Double,
	@SerializedName("lon") val lon: Double
)