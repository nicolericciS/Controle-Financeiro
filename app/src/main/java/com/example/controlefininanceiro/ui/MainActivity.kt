package com.example.controlefininanceiro.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuProvider
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.controlefininanceiro.R
import com.example.controlefininanceiro.databinding.ActivityMainBinding
import com.example.controlefininanceiro.fragments.CategoryListFragment
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
        setupMenu()

    }


    private fun setupMenu(){

        addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_home, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId){
                    R.id.action_category ->{
                        supportFragmentManager.commit {
                            setReorderingAllowed(true)
                            replace<CategoryListFragment>(R.id.fragment_container_view)
                            addToBackStack(null)
                        }
                    }
                }
                return true
            }
        })
    }
}