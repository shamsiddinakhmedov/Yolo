package com.example.yolo.presentation.view.main

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.yolo.R
import com.example.yolo.databinding.ActivityMainBinding
import com.example.yolo.databinding.TabItemBinding
import com.example.yolo.domain.model.NavigationItemModel
import com.example.yolo.presentation.view.main.navigation.NavigationRVAdapter
import com.example.yolo.presentation.view.main.navigation.RecyclerTouchListener
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationRVAdapter: NavigationRVAdapter

    private val itemsNavigation = arrayListOf(
        NavigationItemModel(R.drawable.home, "Home"),
        NavigationItemModel(R.drawable.ic_popular, "Popular"),
        NavigationItemModel(R.drawable.ic_random, "Random"),
        NavigationItemModel(R.drawable.ic_like, "Like"),
        NavigationItemModel(R.drawable.ic_history, "History"),
        NavigationItemModel(R.drawable.ic_about, "About"),
    )
    private val itemsTabLayout = arrayListOf(
        "All", "New", "Animals", "Technology", "Nature"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Yolo)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        drawerLayout = findViewById(R.id.drawer_layout)
        setSupportActionBar(binding.activityMainToolbar)

        setNavigationDrawer()
        setTabs()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Toast.makeText(this@MainActivity, "${tab?.text}", Toast.LENGTH_SHORT).show()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

        itemsTabLayout.forEach {
            binding.tabLayout.addTab(
                binding.tabLayout.newTab().setText(it)
            )
        }
    }

    private fun setTabs() = with(binding) {
        val tabCount = tabLayout.tabCount

        for (i in 0 until tabCount) {
            val tabview =
                TabItemBinding.inflate(LayoutInflater.from(this@MainActivity), null, false)
            val tabAt = tabLayout.getTabAt(i)
            tabAt?.customView = tabview.root

            tabview.titleTv.text = itemsTabLayout[i]

            if (i == 0) {
                tabview.titleTv.setTextColor(resources.getColor(R.color.white))
                tabview.circleIndicator.visibility = View.VISIBLE
            } else {
                tabview.circleIndicator.visibility = View.INVISIBLE
                tabview.titleTv.setTextColor(resources.getColor(R.color.grey))
            }
        }
    }

    private fun setNavigationDrawer() = with(binding) {
        navigationRv.setHasFixedSize(true)
        navigationRVAdapter = NavigationRVAdapter(itemsNavigation)
        navigationRv.adapter = navigationRVAdapter
        navigationRv.addOnItemTouchListener(RecyclerTouchListener(this@MainActivity, object :
            RecyclerTouchListener.ClickListener {
            override fun onClick(view: View, position: Int) {
                when (position) {
                    0 -> {
                        Toast.makeText(this@MainActivity, "0", Toast.LENGTH_SHORT).show()
                    }
                    1 -> {
                        Toast.makeText(this@MainActivity, "1", Toast.LENGTH_SHORT).show()
                    }
                    2 -> {
                        Toast.makeText(this@MainActivity, "2", Toast.LENGTH_SHORT).show()
                    }
                    3 -> {
                        Toast.makeText(this@MainActivity, "3", Toast.LENGTH_SHORT).show()
                    }
                    4 -> {
                        Toast.makeText(this@MainActivity, "4", Toast.LENGTH_SHORT).show()
                    }
                    5 -> {
                        Toast.makeText(this@MainActivity, "5", Toast.LENGTH_SHORT).show()
                    }

                }
                Handler().postDelayed({
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
                    inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                } catch (e: Exception) {
                    e.stackTrace
                }
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                try {
                    val inputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                } catch (e: Exception) {
                    e.stackTrace
                }
            }
        }
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }
}
