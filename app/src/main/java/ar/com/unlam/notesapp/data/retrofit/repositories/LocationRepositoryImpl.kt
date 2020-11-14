package ar.com.unlam.notesapp.data.retrofit.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import ar.com.unlam.notesapp.data.retrofit.RetrofitLocationService
import ar.com.unlam.notesapp.domain.model.Location
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class LocationRepositoryImpl(private val locationService: RetrofitLocationService) :
    LocationRepository {
    val location = MutableLiveData<Location>()
    override fun getLocation(lat: String, lon: String, callback: (Location) -> Unit) {

        locationService.getLocation(lat, lon)
            .enqueue(object : Callback<Location> {
                override fun onResponse(call: Call<Location>, response: Response<Location>) {
                    if (response.isSuccessful) {
                        callback(response.body()!!)
                    }
                }

                override fun onFailure(call: Call<Location>, t: Throwable) {
                    Log.i("LocationRepositoryImpl", "Hubo un error!!!")
                }
            })
    }
}

