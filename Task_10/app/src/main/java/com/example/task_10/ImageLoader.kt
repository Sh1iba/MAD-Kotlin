package com.example.task_10

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response

class ImageLoader(private val api: ImageApi) {
    suspend fun loadImage(url: String): Response<ResponseBody>? {
        return withContext(Dispatchers.IO) {
            try {
                api.getImage(url)
            } catch (e: Exception) {
                null
            }
        }
    }
}