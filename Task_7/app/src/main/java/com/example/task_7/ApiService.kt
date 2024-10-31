package com.example.task_7

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ImageApi {
    @GET
    suspend fun getImage(@Url url: String): Response<ResponseBody>
}

