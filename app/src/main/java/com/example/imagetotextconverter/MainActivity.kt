package com.example.imagetotextconverter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var captureBtn : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        captureBtn = findViewById(R.id.idBtnCapture)

        captureBtn.setOnClickListener{
            var i :Intent = Intent(this , ScannerActivity::class.java)
            startActivity(i)
        }
    }
}