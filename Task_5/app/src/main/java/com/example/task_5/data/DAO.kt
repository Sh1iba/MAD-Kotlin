package com.example.task_5.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

//Интерфейс для доступа к базе данных. Определяет методы для вставки, получения и удаления продуктов.
//предоставляет методы, которые остальная часть приложения использует для взаимодействия с данными в products таблице
@Dao
interface DAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<Product>)

    @Query("SELECT * FROM products")
    suspend fun getAllProducts(): List<Product>

    @Query("DELETE FROM products")
    suspend fun deleteAll()
}
