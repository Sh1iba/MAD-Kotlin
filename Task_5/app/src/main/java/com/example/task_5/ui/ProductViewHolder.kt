package com.example.task_5.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task_5.R
import com.example.task_5.data.Product

class ProductAdapter(private val products: List<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName: TextView = itemView.findViewById(R.id.productName)
        val productDescription: TextView = itemView.findViewById(R.id.productDescription)
        val productPrice: TextView = itemView.findViewById(R.id.productPrice)
        val productImage: ImageView = itemView.findViewById(R.id.productImage)
        val productRating: TextView = itemView.findViewById(R.id.productRating)
    }
//onCreateViewHolder: возвращает объект ViewHolder, который будет
//хранить данные по одному объекту
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }
//onBindViewHolder: выполняет привязку объекта ViewHolder к объекту
//элемента по определенной позиции
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.productName.text = product.title
        holder.productDescription.text = product.description
        holder.productPrice.text = "$${product.price}"
        holder.productRating.text = "⭐${product.rating}"

    // Проверка на null перед загрузкой изображения
    val imageUrl = product.thumbnail
    if (imageUrl != null && imageUrl.isNotEmpty()) {
        Log.d("ProductAdapter", "Loading image from URL: $imageUrl")
        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .into(holder.productImage)
    } else {
        Log.e("ProductAdapter", "Image URL is null or empty for product: ${product.title}")
        // Здесь можно установить изображение по умолчанию или скрыть ImageView
        holder.productImage.setImageResource(R.drawable.default_image) // Установите ресурсу по умолчанию
    }
    }

    override fun getItemCount(): Int {
        return products.size
    }
}
