package com.example.controlefininanceiro.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Register(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val title: String,
    val value: Long,
    val categoryId: Long
): java.io.Serializable

@Entity
data class Category(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    val name: String
): java.io.Serializable
