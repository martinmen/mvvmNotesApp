package ar.com.unlam.notesapp.data.retrofit

import ar.com.unlam.notesapp.domain.model.Location
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface LocationService {
    @GET("georef/api/ubicacion?")
   fun getLocation(
        @Query(value = "lat") lat: String,
        @Query(value = "lon") lon: String
    ): Call<Location>

   /* suspend fun getLocation(
        @Query(value = "lat") lat: String,
        @Query(value = "lon") lon: String
    ): Call<Location>*/
}