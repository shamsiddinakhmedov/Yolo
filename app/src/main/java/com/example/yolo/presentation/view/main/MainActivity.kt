package com.example.yolo.presentation.view.main

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.core.view.MenuProvider
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import com.example.yolo.R
import com.example.yolo.app.common.Constants
import com.example.yolo.databinding.ActivityMainBinding
import com.example.yolo.presentation.view.navigationDrawer.NavigationRVAdapter
import com.example.yolo.presentation.view.navigationDrawer.RecyclerTouchListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationRVAdapter: NavigationRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Yolo)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        drawerLayout = findViewById(R.id.drawer_layout)
        setSupportActionBar(binding.activityMainToolbar)

        setNavigationDrawer()

        setMenu()
    }

    private fun setMenu() {
        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)
                val searchItem = menu.findItem(R.id.search)
                val searchView = searchItem.actionView as SearchView
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        if (query != null) {
                            Navigation.findNavController(this@MainActivity, R.id.mainFragment)
                                .navigate(R.id.folderFragment)
                        }
                        return true
                    }

                    override fun onQueryTextChange(p0: String?): Boolean = true
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        })
    }

    private fun setNavigationDrawer() = with(binding) {
        navigationRv.setHasFixedSize(true)
        navigationRVAdapter = NavigationRVAdapter(Constants.itemsNavigation)
        navigationRv.adapter = navigationRVAdapter
        navigationRv.addOnItemTouchListener(RecyclerTouchListener(this@MainActivity, object :
            RecyclerTouchListener.ClickListener {
            override fun onClick(view: View, position: Int) {
                when (position) {
                    0 -> {
                        Toast.makeText(this@MainActivity, "0", Toast.LENGTH_SHORT).show()
                    }
                    1 -> {
                    }
                    2 -> {
                    }
                    3 -> {
                    }
                    4 -> {
                    }
                    5 -> {
                    }

                }
                Handler(Looper.getMainLooper()).postDelayed({
                    drawerLayout.closeDrawer(GravityCompat.START)
                }, 200)
            }
        }))

        val toggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this@MainActivity,
            drawerLayout,
            activityMainToolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ) {
            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                try {
                    val inputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(
                        currentFocus!!.windowToken,
                        0
                    )
                } catch (e: Exception) {
                    e.stackTrace
                }
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                try {
                    val inputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(
                        currentFocus!!.windowToken,
                        0
                    )
                } catch (e: Exception) {
                    e.stackTrace
                }
            }
        }
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

}
