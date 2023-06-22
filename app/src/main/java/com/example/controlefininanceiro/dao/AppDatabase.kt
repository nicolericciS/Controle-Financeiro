package com.example.controlefininanceiro.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.controlefininanceiro.model.Register

@Database(entities = [Register::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun registerDao(): RegisterDao

    companion object {
        private lateinit var db: AppDatabase

        fun getInstance(context: Context): AppDatabase {

            if (::db.isInitialized) return db

            synchronized(AppDatabase::class) {
                db = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java, "controlefinanceiro.db"
                )
                    .allowMainThreadQueries()
                    .build()
            }

            return db
        }
    }
}




