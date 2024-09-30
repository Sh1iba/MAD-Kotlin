package com.example.prak_4

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment.getExternalStoragePublicDirectory
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prak_4.Classes.GalleryActivity
import com.example.prak_4.Classes.RecyclerAdapter
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.script.dependencies.Environment

class MainActivity : AppCompatActivity() {
    //cameraExecutor: Объявляет переменную типа ExecutorService, которая будет использоваться для выполнения операций с камерой в фоновом потоке.
    //viewFinder: Объявляет переменную типа PreviewView, которая будет отображать предварительный просмотр камеры.
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var viewFinder: PreviewView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cameraExecutor = Executors.newSingleThreadExecutor()
        viewFinder = findViewById(R.id.previewView)

        // Инициализация кнопки
        val photoButton: Button = findViewById(R.id.photoButton)
        photoButton.setOnClickListener {
            saveDateToFile()
        }

        val galleryButton: Button = findViewById(R.id.galleryButton)
        galleryButton.setOnClickListener {
            val intent = Intent(this, GalleryActivity::class.java)
            startActivity(intent)
        }

         //Проверяет, предоставлены ли все необходимые разрешения с помощью метода allPermissionsGranted().
         //Если разрешения предоставлены, запускает камеру, иначе запрашивает разрешения у пользователя.

        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
    }
//Метод, который инициализирует и запускает камеру.
    private fun startCamera() {
        //Получает ProcessCameraProvider, который управляет жизненным циклом камеры.
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            try {
                //Внутри блока try пытается получить экземпляр cameraProvider.
                //Создает объект Preview, который будет отображать предварительный просмотр камеры, и связывает его с viewFinder.
                val cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(viewFinder.surfaceProvider)
                }
                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                //Связывает объект preview с жизненным циклом активности, что позволяет автоматически управлять ресурсами камеры.
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview)
            } catch (exc: Exception) {
                Log.e("MainActivity", "Error starting camera", exc)
                Toast.makeText(this, "Camera initialization failed", Toast.LENGTH_SHORT).show()
            }
        }, ContextCompat.getMainExecutor(this))
    }

//Проверяет, предоставлены ли все необходимые разрешения, и возвращает true или false.
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
//Если код запроса совпадает, проверяет, были ли предоставлены разрешения. Если да, запускает камеру. Если нет, показывает сообщение и закрывает активность.
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(this, "Camera permission required", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    private fun saveDateToFile() {
        // Получаем текущее время
        val currentDateTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

        // Создаем файл в папке "Documents" внутри директории приложения
        val documentsDir = File(getExternalStoragePublicDirectory(android.os.Environment.DIRECTORY_DOCUMENTS), "photos")
        if (!documentsDir.exists()) {
            documentsDir.mkdirs() // Создает папку, если она не существует
        }

        val file = File(documentsDir, "date.txt")

        // Сохраняем дату и время в файл
        try {
            //Используется блок try, чтобы перехватить возможные исключения.
            //FileOutputStream(file, true) открывает поток для записи в файл, где true означает, что данные будут добавляться в конец файла.
            //Метод write записывает строку с текущей датой и временем, преобразованную в массив байтов.
            FileOutputStream(file, true).use { fos ->
                fos.write("$currentDateTime\n".toByteArray())
            }
            Toast.makeText(this, "Date saved: $currentDateTime", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            Log.e("MainActivity", "Error writing to file")
            Toast.makeText(this, "Failed to save date", Toast.LENGTH_SHORT).show()
        }
    }



//REQUEST_CODE_PERMISSIONS: Код, используемый для идентификации запроса разрешений.
//REQUIRED_PERMISSIONS: Массив необходимых разрешений для работы с камерой
// (в данном случае только Manifest.permission.CAMERA).
    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }
}
