package ar.com.unlam.notesapp.data.retrofit

import ar.com.unlam.notesapp.domain.model.Location
import retrofit2.Call

interface RetrofitLocationService {

   //suspend fun getLocation(lat: String, lon: String): Call<Location>
   fun getLocation(lat: String, lon: String): Call<Location>
}