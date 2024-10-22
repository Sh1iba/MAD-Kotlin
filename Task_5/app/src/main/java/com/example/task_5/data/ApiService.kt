package com.example.task_5.data;

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("products") // Этот эндпоинт добавляется к BASE_URL и  будет выполнять
    // HTTP GET запрос на указанный эндпоинт.
    fun getProducts(): Call<ProductResponse>
}
