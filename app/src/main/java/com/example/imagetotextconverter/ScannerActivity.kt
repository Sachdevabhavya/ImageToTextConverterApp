package com.example.imagetotextconverter

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class ScannerActivity : AppCompatActivity() {

    private lateinit var imgCapture: ImageView
    private lateinit var resultText: TextView
    private lateinit var detectBtn: Button
    private lateinit var snapBtn: Button
    private var imgBitmap: Bitmap? = null
    private val imageRequestCapture: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)

        imgCapture = findViewById(R.id.idIVCaptureImage)
        resultText = findViewById(R.id.idTVDetectedText)
        detectBtn = findViewById(R.id.idButtonDetect)
        snapBtn = findViewById(R.id.idButtonSnap)

        detectBtn.setOnClickListener {
            detectText()
        }

        snapBtn.setOnClickListener {
            if (checkPermission()) {
                captureImage()
            } else {
                requestPermission()
            }
        }
    }

    private fun checkPermission(): Boolean {
        val cameraPermission: Int = ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA)
        return cameraPermission == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        val permissionCode: Int = 200
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), permissionCode)
    }

    private fun captureImage() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, imageRequestCapture)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 200) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted !!", Toast.LENGTH_SHORT).show()
                captureImage()
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == imageRequestCapture && resultCode == RESULT_OK) {
            val extras = data?.extras
            imgBitmap = extras?.get("data") as? Bitmap
            imgBitmap?.let {
                imgCapture.setImageBitmap(it)
            }
        }
    }

    private fun detectText() {
        imgBitmap?.let { bitmap ->
            val inputImage = InputImage.fromBitmap(bitmap, 0)
            val textRecognizer: TextRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

            val result: Task<Text> = textRecognizer.process(inputImage)
                .addOnSuccessListener { text ->
                    val detectedText = StringBuilder()
                    for (block in text.textBlocks) {
                        for (line in block.lines) {
                            for (element in line.elements) {
                                detectedText.append(element.text).append(" ")
                            }
                        }
                    }
                    resultText.text = detectedText.toString()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(applicationContext, "Failed to detect text: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } ?: run {
            Toast.makeText(this, "No image captured", Toast.LENGTH_SHORT).show()
        }
    }
}