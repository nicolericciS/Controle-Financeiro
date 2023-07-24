package com.example.controlefininanceiro.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Register(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val title: String,
    val value: Long
): java.io.Serializable
