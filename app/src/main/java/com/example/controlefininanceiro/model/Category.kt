package com.example.controlefininanceiro.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Category(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    val name: String
): Serializable {
    override fun toString(): String {
        return name
    }
}
