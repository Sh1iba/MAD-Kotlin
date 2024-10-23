package com.example.task_7

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.signature.ObjectKey

import android.view.View
import android.widget.Toast
import com.example.task_7.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var clipboard: ClipboardManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager

        binding.button.setOnClickListener {
            val imageUrl : String = binding.editText.text.toString()
            loadImage(imageUrl)
        }
    }

    fun copyPey(view: View) {
        val textToCopy = "https://random-image-pepebigotes.vercel.app/api/random-image"
        Log.d("Копирование", "Текст для копирования: $textToCopy")
        val clip = ClipData.newPlainText("text", textToCopy)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this, "Текст скопирован", Toast.LENGTH_SHORT).show()

    }
    fun copySkull(view: View) {
        val textToCopy = "https://random-image-pepebigotes.vercel.app/api/skeleton-random-image"
        Log.d("Копирование", "Текст для копирования: $textToCopy")
        val clip = ClipData.newPlainText("text", textToCopy)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this, "Текст скопирован", Toast.LENGTH_SHORT).show()

    }


    private fun loadImage(url: String) {
        // Используем текущий timestamp как уникальный ключ
        Glide.with(this)
            .load(url)
            .signature(ObjectKey(System.currentTimeMillis())) // Принудительная перезагрузка
            .diskCacheStrategy(DiskCacheStrategy.NONE) // Отключение кэширования
            .into(binding.imageView)
    }
}

