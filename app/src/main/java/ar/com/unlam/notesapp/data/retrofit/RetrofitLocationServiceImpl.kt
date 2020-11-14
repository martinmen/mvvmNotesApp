package ar.com.unlam.notesapp.data.retrofit

import ar.com.unlam.notesapp.domain.model.Location
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitLocationServiceImpl : RetrofitLocationService {

    private val service: LocationService = Retrofit.Builder()
        .baseUrl("https://apis.datos.gob.ar")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(LocationService::class.java)

    override fun getLocation(lat: String, lon: String): Call<Location> {
        return service.getLocation(lat, lon)
    }


    /*override fun getLocation(term: String): Call<Location> {
            return service.getLocation(term)
    }*/
}