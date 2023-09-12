package com.example.controlefininanceiro.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.controlefininanceiro.R
import com.example.controlefininanceiro.databinding.ActivityMainBinding
import com.example.controlefininanceiro.fragments.CategoryListFragment
import com.example.controlefininanceiro.fragments.ListFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<ListFragment>(binding.fragmentContainerView.id)
            }
        }
        setupNav()

    }

    private fun setupNav() {
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {

                R.id.registers -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<ListFragment>(R.id.fragment_container_view)
                        addToBackStack(null)
                    }
                }

                R.id.category -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<CategoryListFragment>(R.id.fragment_container_view)
                        addToBackStack(null)
                    }
                }
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
        }
        return super.onOptionsItemSelected(item)
    }
}