package sv.edu.ufg.am.containproviderpocasangre

import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import sv.edu.ufg.am.containprovidersanchez.R
import java.io.File
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {


    private lateinit var captura : ImageView
    private lateinit var urlImage : Uri
    private lateinit var tiempo: String
    private lateinit var nombreImagen : String

    private var contract = registerForActivityResult(ActivityResultContracts.TakePicture()){
        captura.setImageURI(null)
        captura.setImageURI(urlImage)
    }

    private fun createImageUri():Uri{
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tiempo = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss").withZone(ZoneOffset.UTC).format(Instant.now())
        }


        nombreImagen = "$tiempo.png"
        val imagen = File(filesDir,nombreImagen)

        return FileProvider.getUriForFile(this, "sv.edu.ufg.am.containprovidersanchez.FileProvider",imagen)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        captura = findViewById(R.id.captureImageView)

        val captureImgBtn = findViewById<Button>(R.id.CaptureImgbtn)

        captureImgBtn.setOnClickListener {
            urlImage = createImageUri()
            contract.launch(urlImage)

        }
    }
}