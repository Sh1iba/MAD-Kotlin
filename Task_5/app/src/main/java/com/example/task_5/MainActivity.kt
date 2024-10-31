package com.example.task_5

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.task_5.data.ApiService
import com.example.task_5.data.DAO
import com.example.task_5.data.Database
import com.example.task_5.data.Product
import com.example.task_5.ui.ProductAdapter
import com.example.task_5.data.ProductResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter
    private val productList = mutableListOf<Product>() // Список для хранения продуктов

    private lateinit var productDao: DAO

    @Inject
    lateinit var productApi: ApiService // Инъекция ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//создаю бд
        val database = Room.databaseBuilder(
            applicationContext,
            Database::class.java, "database"
        ).build()

        //Инициализируем productDao для доступа к методам базы данных.
        productDao = database.productDao()

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = ProductAdapter(productList)
        recyclerView.adapter = adapter

        getProducts()
    }
    private fun getProducts() {
        /*
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val productApi = retrofit.create(ApiService::class.java)
*/
        productApi.getProducts().enqueue(object : Callback<ProductResponse> {
            override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
                response.body()?.let { productResponse ->
                    lifecycleScope.launch {
                        loadProductsFromDatabase()
                    }
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Log.d("Fail", t.message.toString())
            }
        })
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun loadProductsFromDatabase() {
        lifecycleScope.launch {
            productList.addAll(productDao.getAllProducts())
            adapter.notifyDataSetChanged()
        }
    }

}
