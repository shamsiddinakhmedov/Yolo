package com.example.yolo.presentation.view.main

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.yolo.R
import com.example.yolo.app.common.Constants.LATEST
import com.example.yolo.app.common.Constants.ORDER_BY
import com.example.yolo.app.common.Constants.POPULAR
import com.example.yolo.app.common.Constants.itemsNavigation
import com.example.yolo.app.observeNetworkState.ConnectivityObserver
import com.example.yolo.app.observeNetworkState.NetworkConnectivityObserver
import com.example.yolo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var connectivityObserver: ConnectivityObserver
    private var actionBarDrawerToggle: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Yolo)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = itemsNavigation[0].title
        setNavigationView()

        observeNetworkChanges()
    }

    private fun setNavigationView() {
        binding.navView.itemIconTintList = null
        actionBarDrawerToggle =
            ActionBarDrawerToggle(
                this,
                binding.myDrawerLayout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            )

        binding.myDrawerLayout.addDrawerListener(actionBarDrawerToggle!!)
        actionBarDrawerToggle!!.syncState()

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    binding.myDrawerLayout.closeDrawer(GravityCompat.START)
                    val bundle = Bundle()
                    bundle.putString(ORDER_BY, LATEST)
                    binding.navHostFragment.findNavController()
                        .navigate(R.id.unsplashFragment, bundle)
                    true
                }
                R.id.popular -> {
                    binding.myDrawerLayout.closeDrawer(GravityCompat.START)
                    val bundle = Bundle()
                    bundle.putString(ORDER_BY, POPULAR)
                    binding.navHostFragment.findNavController()
                        .navigate(R.id.unsplashFragment, bundle)
                    true
                }
                R.id.random -> {
                    title = itemsNavigation[2].title
                    binding.myDrawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.like -> {
                    title = itemsNavigation[3].title
                    binding.myDrawerLayout.closeDrawer(GravityCompat.START)
                    binding.navHostFragment.findNavController().navigate(R.id.likePhotosFragment)
                    true
                }
                R.id.history -> {
                    title = itemsNavigation[4].title
                    binding.myDrawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                else -> {
                    title = itemsNavigation[5].title
                    binding.myDrawerLayout.closeDrawer(GravityCompat.START)
                    binding.navHostFragment.findNavController().navigate(R.id.aboutFragment)
                    true
                }
            }
        }
    }

    override fun onBackPressed() {

        if (binding.navHostFragment.findNavController().currentDestination?.id == R.id.unsplashFragment) {
            finish()
        }
        super.onBackPressed()
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
