package com.example.prak_4.Classes

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Environment.getExternalStoragePublicDirectory
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prak_4.R
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException

class GalleryActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Считываем даты из файла
        val dates = readDatesFromFile()

        // Если список пустой
        if (dates.isEmpty()) {
            Toast.makeText(this, "Нет данных для отображения", Toast.LENGTH_SHORT).show()
        }

        adapter = RecyclerAdapter(dates)
        recyclerView.adapter = adapter
    }

    private fun readDatesFromFile(): List<String> {
        val dates = mutableListOf<String>()
        val documentsDir = File(getExternalStoragePublicDirectory(android.os.Environment.DIRECTORY_DOCUMENTS), "photos")
        val file = File(documentsDir, "date.txt")

        if (file.exists()) {
            try {
                BufferedReader(FileReader(file)).use { reader ->
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        dates.add(line!!)
                    }
                }
            } catch (e: IOException) {
                Log.e("GalleryActivity", "Error reading from file", e)
            }
        } else {
            Log.e("GalleryActivity", "File does not exist")
        }

        return dates.sorted() // Сортировка в хронологическом порядке
    }
}
