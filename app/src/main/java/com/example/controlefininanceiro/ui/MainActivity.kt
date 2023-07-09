package com.example.controlefininanceiro.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.controlefininanceiro.databinding.ActivityMainBinding
import com.example.controlefininanceiro.fragments.ListFragment

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
                add<ListFragment>(binding.fragmentContainerView.id)
            }
        }

    }
}