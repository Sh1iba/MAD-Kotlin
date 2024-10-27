package com.example.task_7

import android.content.ClipData
import android.content.ClipboardManager
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.signature.ObjectKey
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.task_7.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var clipboard: ClipboardManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager

    }

    fun Network(view : View) {

        val url : String = binding.editText.text.toString()
        lifecycleScope.launch {
            val bitmap = withContext(Dispatchers.IO) {
                try {
                    // Загружаем изображение в фоновом потоке
                    Glide.with(this@MainActivity)
                        .asBitmap()
                        .load(url)
                        .signature(ObjectKey(System.currentTimeMillis()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .submit()
                        .get() // Получаем результат
                } catch (e: Exception) {
                    Log.e("LoadImage", "Ошибка загрузки изображения", e)
                    null
                }
            }

            if (bitmap != null) {
                binding.imageView.setImageBitmap(bitmap)
                Disk(bitmap, "downloaded_image.png")
            } else {
                Toast.makeText(this@MainActivity, "Ошибка загрузки изображения", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun Disk(bitmap: Bitmap, fileName: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val file = File(filesDir, fileName)
                FileOutputStream(file).use { out ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out) // Сохраняем изображение
                }
            } catch (e: Exception) {
                Log.e("SaveImage", "Ошибка сохранения изображения", e)
            }
        }
    }

    fun copyLandScape(view: View) {
        val textToCopy = "https://random-image-pepebigotes.vercel.app/api/random-image"
        Log.d("Копирование", "Текст для копирования: $textToCopy")
        val clip = ClipData.newPlainText("text", textToCopy)
        clipboard.setPrimaryClip(clip)

    }

    fun copySkull(view: View) {
        val textToCopy = "https://random-image-pepebigotes.vercel.app/api/skeleton-random-image"
        Log.d("Копирование", "Текст для копирования: $textToCopy")
        val clip = ClipData.newPlainText("text", textToCopy)
        clipboard.setPrimaryClip(clip)

    }
}

