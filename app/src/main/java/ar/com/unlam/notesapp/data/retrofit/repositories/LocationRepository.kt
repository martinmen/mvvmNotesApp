package ar.com.unlam.notesapp.data.retrofit.repositories

import androidx.lifecycle.MutableLiveData
import ar.com.unlam.notesapp.domain.model.Location
import retrofit2.Callback


interface LocationRepository {
    //fun getLoction(query: String, callback: (Location) -> Unit, onError: (Location) -> Unit)

    fun getLocation(lat: String, lon:String)

}