package ar.com.unlam.notesapp.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import ar.com.unlam.notesapp.R
import ar.com.unlam.notesapp.databinding.ActivityAddNoteBinding
import ar.com.unlam.notesapp.domain.model.Note
import ar.com.unlam.notesapp.ui.viewModels.AddNoteViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_note.*
import org.koin.android.viewmodel.ext.android.viewModel


class AddNoteActivity : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var idNota: Long? = null
    private val viewModel: AddNoteViewModel by viewModel()
    private lateinit var binding: ActivityAddNoteBinding
    private val SELECT_ACTIVITY = 50
    private var imageUri: Uri? = null

    companion object {
        private const val locationRequestCode = 1000
        const val NOTE_ID = "idNote"
        const val REQUEST_GALLERY = 1001
        const val REQUEST_CAMARA = 1002
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setObservers()
        setListeners()
        handleIntent()
    }

    private fun handleIntent() {

        if (intent.hasExtra(NOTE_ID)) {
            var idNote = intent.getLongExtra(NOTE_ID, 0)
            viewModel.getNoteById(idNote)
            viewModel.noteLiveData.observe(this, {
                binding.editTextTituloNota.setText(it.titulo)
                binding.editTextComentarioNota.setText(it.comentario)
                binding.textViewMuniciapio.setText(it.municipio)
                binding.textViewProvincia.setText(it.provincia)
                imageUri = it.imagen?.toUri()
                binding.AddImage.setImageURI(imageUri)
                idNota = it.id
            })
        }
    }

    private fun setObservers() {
        viewModel.locationLiveData.observe(this, {
            onLocationChange(it.ubicacion.provincia.nombre, it.ubicacion.municipio.nombre)
        })
    }

    private fun setListeners() {
        binding.buttonAddLocation.setOnClickListener {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
            checkForPermission()
        }
        binding.buttonAddNote.setOnClickListener {

            val note = Note(
                titulo = binding.editTextTituloNota.text.toString(),
                comentario = binding.editTextComentarioNota.text.toString(),
                provincia = binding.textViewProvincia.text.toString(),
                municipio = binding.textViewMuniciapio.text.toString(),
                imagen = imageUri.toString()
            )

            db.collection("notas")
                .add(note)

            if (viewModel.verifyRequeried(note)) {
                viewModel.checkAddOrUpdate(idNota, note)
                this@AddNoteActivity.finish()
            } else {
                Toast.makeText(this, getString(R.string.ErrorTitleEmpty), Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonAddImagGallery.setOnClickListener {
            // ImageController.selectPhotoFromGallery(this, SELECT_ACTIVITY)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //Preguntar si tiene permisos
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {//Pedir permisos
                    val permisosArchivos = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permisosArchivos, REQUEST_GALLERY)
                } else {
                    //entonces si tiene permisos
                    openGallery()
                }
            } else {
                //tiene version lollypop hacia abajo y por default tiene permisos
                openGallery()
            }
        }
        binding.buttonAddImagCamara.setOnClickListener {
            // ImageController.selectPhotoFromGallery(this, SELECT_ACTIVITY)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //Preguntar si tiene permisos
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                ) {//Pedir permisos
                    val permisosArchivos = arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                    )
                    requestPermissions(permisosArchivos, REQUEST_CAMARA)
                } else {
                    //entonces si tiene permisos
                    openCamara()
                }
            } else {
                //tiene version lollypop hacia abajo y por default tiene permisos
                openCamara()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_GALLERY -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    openGallery()
                else
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.noAccesWithOutPermissionCamara),
                        Toast.LENGTH_SHORT
                    ).show()
            }
            REQUEST_CAMARA -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    openCamara()
                else
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.AllowPermissionToOpenCamara),
                        Toast.LENGTH_SHORT
                    ).show()
            }
        }
    }

    private fun openGallery() {
        val intentGaleria = Intent(Intent.ACTION_PICK)
        intentGaleria.type = "image/*"
        //
        val value = ContentValues()
        value.put(MediaStore.Images.Media.TITLE, "Nueva imagen")
        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, value)
        val camaraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        camaraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        //
        startActivityForResult(intentGaleria, REQUEST_GALLERY)
    }

    private fun openCamara() {
        //ConentValues es como un manejador de contenidos. Se suele utilizar en BD. Crea un espaciode memoria vacia en el que
        //vamos a poner los bits de la foto
        val value = ContentValues()
        value.put(MediaStore.Images.Media.TITLE, "Nueva imagen")
        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, value)
        val camaraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        camaraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(camaraIntent, REQUEST_CAMARA)
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

    @SuppressLint("MissingPermission")
    fun getLatitudLongitud() {
        fusedLocationProviderClient.lastLocation.addOnSuccessListener(this) { location ->
            if (location != null) {
                val latitud = location.latitude.toString()
                val longitud = location.longitude.toString()
                viewModel.getLocation(latitud, longitud)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            SELECT_ACTIVITY -> {
                resultCode == Activity.RESULT_OK
                // imageUri = data!!.data
                // AddImage.setImageURI(imageUri)
            }
            REQUEST_GALLERY -> {
                if (resultCode == Activity.RESULT_OK) {

                    binding.AddImage.setImageURI(data?.data)
                }
            }
            REQUEST_CAMARA -> {
                if (resultCode == Activity.RESULT_OK) {
                    binding.AddImage.setImageURI(imageUri)
                }
            }

        }
    }

}
