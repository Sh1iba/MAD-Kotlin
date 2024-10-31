package com.example.task_7

import android.content.ClipData
import android.content.ClipboardManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.task_7.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var clipboard: ClipboardManager
    private lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager

        // Инициализируем ImageLoader с вашим API
        val api = RetrofitClient.imageApi // Получаем экземпляр API
        imageLoader = ImageLoader(api)
    }

    fun Network(view: View) {
        val url: String = binding.editText.text.toString()
        lifecycleScope.launch {
            val response = imageLoader.loadImage(url) ?: return@launch
            if (response.isSuccessful) {
                val inputStream = response.body()?.byteStream()
                val bitmap = BitmapFactory.decodeStream(inputStream)
                if (bitmap != null) {
                    binding.imageView.setImageBitmap(bitmap)
                    saveImage(bitmap, "downloaded_image.png")
                } else {
                    showToast("Ошибка обработки изображения")
                }
            } else {
                showToast("Ошибка загрузки изображения")
            }
        }
    }

    private fun saveImage(bitmap: Bitmap, fileName: String) {
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

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun copyLandScape(view: View) {
        val textToCopy = "https://random-image-pepebigotes.vercel.app/api/random-image"
        Log.d("RRR", "Текст для копирования: $textToCopy")
        val clip = ClipData.newPlainText("text", textToCopy)
        clipboard.setPrimaryClip(clip)
    }

    fun copySkull(view: View) {
        val textToCopy = "https://random-image-pepebigotes.vercel.app/api/skeleton-random-image"
        Log.d("RRR", "Текст для копирования: $textToCopy")
        val clip = ClipData.newPlainText("text", textToCopy)
        clipboard.setPrimaryClip(clip)
    }
    fun paste(view: View) {
        val clip = clipboard.primaryClip
        if (clip != null && clip.itemCount > 0) {
            val text = clip.getItemAt(0).text
            binding.editText.setText(text) // Вставляем текст в EditText
        }
    }

}


