package com.example.yolo.presentation.view.main

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.yolo.R
import com.example.yolo.app.observeNetworkState.ConnectivityObserver
import com.example.yolo.app.observeNetworkState.NetworkConnectivityObserver
import com.example.yolo.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var connectivityObserver: ConnectivityObserver

    var drawerLayout: DrawerLayout? = null
    private var actionBarDrawerToggle: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Yolo)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setNavigationView()

        observeNetworkChanges()
    }

    private fun setNavigationView() {
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.itemIconTintList = null

        drawerLayout = findViewById(R.id.my_drawer_layout)
        actionBarDrawerToggle =
            ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            )

        binding.myDrawerLayout.addDrawerListener(actionBarDrawerToggle!!)
        actionBarDrawerToggle!!.syncState()

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.popular -> {
                    clickPopular()
                    true
                }
                R.id.home -> {
                    Toast.makeText(this, "home", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> {
                    true
                }
            }
        }

    }

    private fun clickPopular() {
//        binding.navHostFragment.findNavController().navigate()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle!!.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun observeNetworkChanges() {
        connectivityObserver = NetworkConnectivityObserver(this)
        connectivityObserver.observer().onEach {
        }.launchIn(lifecycleScope)
    }
}
