package com.example.lab07

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE_WRITE_PERM = 401
    private lateinit var tvData: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvData = findViewById(R.id.tvData)

        findViewById<Button>(R.id.btnWriteFile).setOnClickListener {
            writeFile("Hello: ${Date(System.currentTimeMillis())}")
        }

        findViewById<Button>(R.id.btnReadFile).setOnClickListener {
            val data = readFile()
            tvData.text = data
        }

        requestStoragePermission()
    }

    private fun requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_CODE_WRITE_PERM
            )
        }
    }

    private fun writeFile(data: String) {
        try {
            val fileOutput = openFileOutput("test.txt", MODE_PRIVATE)
            fileOutput.write(data.toByteArray())
            fileOutput.close()
            Toast.makeText(this, "File written", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Write failed: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }


    private fun readFile(): String {
        return try {
            val fileInput = openFileInput("test.txt")
            val reader = BufferedReader(InputStreamReader(fileInput))
            val content = reader.readText()
            reader.close()
            content
        } catch (e: Exception) {
            e.printStackTrace()
            "Read failed: ${e.message}"
        }
    }

}
