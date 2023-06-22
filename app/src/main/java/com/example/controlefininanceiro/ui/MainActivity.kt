package com.example.controlefininanceiro.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.room.Room
import com.example.controlefininanceiro.dao.AppDatabase
import com.example.controlefininanceiro.dao.RegisterDao
import com.example.controlefininanceiro.databinding.ActivityMainBinding
import com.example.controlefininanceiro.fragments.ListaFragment
import com.example.controlefininanceiro.model.Register

class MainActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if(savedInstanceState ==  null){
            supportFragmentManager.commit{
                setReorderingAllowed(true)
                add<ListaFragment>(binding.fragmentContainerView.id)
            }
        }

    }
}