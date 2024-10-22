package com.example.task_5.data

import androidx.room.Database
import androidx.room.RoomDatabase

//Database определяет конфигурацию базы данных и служит основной точкой доступа приложения к сохраненным данным
@Database(entities = [Product::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun productDao(): DAO
}
