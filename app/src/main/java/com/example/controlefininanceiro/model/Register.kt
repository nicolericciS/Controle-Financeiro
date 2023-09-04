package com.example.controlefininanceiro.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Register(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title: String,
    val value: Long,
    @Embedded(prefix = "category_") val category: Category
): Serializable

