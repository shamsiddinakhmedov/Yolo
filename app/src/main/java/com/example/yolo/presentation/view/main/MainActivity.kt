package com.example.yolo.presentation.view.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.yolo.databinding.ActivityMainBinding


//@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(com.example.yolo.R.style.Theme_Yolo)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drawer = findViewById<View>(com.example.yolo.R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
            this,
            drawer,
            findViewById(com.example.yolo.R.id.toolbar),
            com.example.yolo.R.string.navigation_drawer_open,
            com.example.yolo.R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()
    }
}