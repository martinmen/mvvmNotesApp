package ar.com.unlam.notesapp.domain.model
import com.google.gson.annotations.SerializedName

data class Ubicacion (

	@SerializedName("departamento") val departamento : Departamento,
	@SerializedName("lat") val lat : Double,
	@SerializedName("lon") val lon : Double,
	@SerializedName("municipio") val municipio : Municipio,
	@SerializedName("provincia") val provincia : Provincia
)