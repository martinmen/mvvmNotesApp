package ar.com.unlam.notesapp.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import ar.com.unlam.notesapp.R
import ar.com.unlam.notesapp.databinding.ActivityAddNoteBinding
import ar.com.unlam.notesapp.domain.model.Note
import ar.com.unlam.notesapp.ui.viewModels.AddNoteViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.android.viewmodel.ext.android.viewModel


class AddNoteActivity : AppCompatActivity() {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var idNota: Long? = null

    companion object {
        private const val locationRequestCode = 1000
    }

    private val viewModel: AddNoteViewModel by viewModel()
    private lateinit var binding: ActivityAddNoteBinding
    /*  //Implementacion sin Koin //  private val viewModel: AddNoteViewModel by viewModels { GeneralNoteViewModelFactory(applicationContext, nameActivity ) }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra("idNote")) {
            var idNote = intent.getLongExtra("idNote", 0)
            viewModel.getNoteById(idNote!!)
            viewModel.noteLiveData.observe(this, Observer {
                binding.editTextTituloNota.setText(it.titulo)
                binding.editTextComentarioNota.setText(it.comentario)
                idNota = it.id
            })
        }

        setObservers()

        setListeners()
    }

    private fun setObservers() {
        viewModel.locationLiveData.observe(this, Observer {
            onLocationChange(it.ubicacion.provincia.nombre, it.ubicacion.municipio.nombre)
        })
    }

    override fun onStart() {
        super.onStart()
    }

    private fun setListeners() {
        //Agregar ubicacion obtiene lat y long y hace llamado a la api para obtener Provincia y municipio
        binding.buttonAddLocation.setOnClickListener {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
            checkForPermission()
        }

        binding.buttonAddNote.setOnClickListener {
            if (binding.editTextTituloNota.text.isNullOrEmpty()) {
                Toast.makeText(this, getString(R.string.name_must_completed), Toast.LENGTH_LONG)
                    .show()
            } else {
                val note = Note(
                    titulo = binding.editTextTituloNota.text.toString(),
                    comentario = binding.editTextComentarioNota.text.toString(),
                    provincia = binding.textViewProvincia.text.toString(),
                    municipio = binding.textViewMuniciapio.text.toString()
                )
                viewModel.checkAddOrUpdate(idNota, note)
                this@AddNoteActivity.finish()

            }
        }
    }

    private fun onLocationChange(prov: String, muni: String) {
        binding.textViewProvincia.setText(prov)
        binding.textViewMuniciapio.setText(muni)
    }

    private fun checkForPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                locationRequestCode
            )
        } else {
            getLatitudLongitud()
        }
    }

    fun getLatitudLongitud() {
        fusedLocationProviderClient.lastLocation.addOnSuccessListener(this) { location ->
            if (location != null) {
                val latitud = location.latitude.toString()
                val longitud = location.longitude.toString()
                viewModel.getLocation(latitud, longitud)
            }
        }
    }


/* private fun getExtras(): Long? {
     var idNota1 :Long = 0
     if (intent.hasExtra("idNote")) {
         var idNote = intent.getLongExtra("idNote", 0)
         viewModel.getNoteById(idNote)
         viewModel.noteLiveData.observe(this, Observer {
             binding.editTextTituloNota.setText(it.titulo)
             binding.editTextComentarioNota.setText(it.comentario)
            var idNota1 = it.id
         })
         return idNota1
     }
     return idNota1
 }*/
}


