
package com.example.task_5.data

import androidx.room.Entity
import androidx.room.PrimaryKey

//важно чтобы названия полей класса соответсвтовали  тем что в json иначе информацию не выведет

@Entity(tableName = "products")
data class Product(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val rating: Double,
    val thumbnail: String
)

data class ProductResponse(
    val products: List<Product>
)