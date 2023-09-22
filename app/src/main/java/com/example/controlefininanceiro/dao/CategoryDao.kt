package com.example.controlefininanceiro.dao

import androidx.room.*
import com.example.controlefininanceiro.model.Category

@Dao
interface CategoryDao {

    @Query("SELECt * FROM Category")
    fun searchAll(): List<Category>

    @Insert
     fun save(category: Category)

    @Delete
    fun delete(category: Category)

    @Update
    fun update(category: Category)
}