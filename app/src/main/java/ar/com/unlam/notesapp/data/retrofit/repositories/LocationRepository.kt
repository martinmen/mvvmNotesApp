package ar.com.unlam.notesapp.data.retrofit.repositories

import androidx.lifecycle.MutableLiveData
import ar.com.unlam.notesapp.domain.model.Location
import retrofit2.Callback


interface LocationRepository {

  // suspend fun getLocation(lat: String, lon: String, callback: (Location) -> Unit)
    fun getLocation(lat: String, lon: String, callback: (Location) -> Unit)
}