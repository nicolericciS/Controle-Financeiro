package com.example.controlefininanceiro.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.controlefininanceiro.model.Register

@Dao
interface RegisterDao {

    @Query("SELECt * FROM Register")
    fun searchAll(): List<Register>

    @Insert
    fun save(register: Register)

    @Delete
    fun delete(register: Register)

    @Update
    fun update(register: Register)
}