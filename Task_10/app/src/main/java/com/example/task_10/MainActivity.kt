package com.example.task_10

import android.content.ClipData
import android.content.ClipboardManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.task_10.ui.theme.AppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class MainActivity : ComponentActivity() {

    private lateinit var clipboard: ClipboardManager
    private lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val api = RetrofitClient.imageApi
        imageLoader = ImageLoader(api)

        setContent {
            AppTheme(darkTheme = true) {
                Greeting()
            }
        }
    }
    @Preview(showBackground = true)
    @Composable
    fun Greeting() {
        var url by remember { mutableStateOf("") }
        var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            TextField(
                value = url,
                onValueChange = { url = it },
                label = { Text("Enter your link") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            Button(
                onClick = { Network(url, onImageLoaded = { bitmap -> imageBitmap = bitmap }) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text("Show")
            }

            imageBitmap?.let {
                Image(bitmap = it.asImageBitmap(), contentDescription = null)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = { copyLandScape(clipboard) }) {
                    Text("Copy Landscape")
                }
                Button(onClick = { copySkull(clipboard) }) {
                    Text("Copy Skull")
                }
                Button(onClick = { paste(clipboard, { url = it }) }) {
                    Text("Paste")
                }
            }
        }
    }

    fun Network(url: String, onImageLoaded: (Bitmap) -> Unit) {
        lifecycleScope.launch {
            val response = imageLoader.loadImage(url) ?: return@launch
            if (response.isSuccessful) {
                val inputStream = response.body()?.byteStream()
                val bitmap = BitmapFactory.decodeStream(inputStream)
                if (bitmap != null) {
                    onImageLoaded(bitmap)
                    saveImage(bitmap, "downloaded_image.png")
                } else {
                    showToast("Ошибка обработки изображения")
                }
            } else {
                showToast("Ошибка загрузки изображения")
            }
        }
    }

    fun saveImage(bitmap: Bitmap, fileName: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val file = File(filesDir, fileName)
                FileOutputStream(file).use { out ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out) // Save the image
                }
            } catch (e: Exception) {
                Log.e("SaveImage", "Ошибка сохранения изображения", e)
            }
        }
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    fun copyLandScape(clipboard: ClipboardManager) {
        val textToCopy = "https://random-image-pepebigotes.vercel.app/api/random-image"
        Log.d("RRR", "Текст для копирования: $textToCopy")
        val clip = ClipData.newPlainText("text", textToCopy)
        clipboard.setPrimaryClip(clip)
    }

    fun copySkull(clipboard: ClipboardManager) {
        val textToCopy = "https://random-image-pepebigotes.vercel.app/api/skeleton-random-image"
        Log.d("RRR", "Текст для копирования: $textToCopy")
        val clip = ClipData.newPlainText("text", textToCopy)
        clipboard.setPrimaryClip(clip)
    }

    fun paste(clipboard: ClipboardManager, setUrl: (String) -> Unit) {
        val clip = clipboard.primaryClip
        if (clip != null && clip.itemCount > 0) {
            val text = clip.getItemAt(0).text
            setUrl(text.toString()) // Update the URL state
        }
    }
}
