package com.example.yolo.presentation.view.fragment.unsplash_main

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.navigation.fragment.findNavController
import com.example.yolo.R
import com.example.yolo.app.common.Constants.itemsTabLayout
import com.example.yolo.databinding.FragmentUnsplashBinding
import com.example.yolo.presentation.architecture.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UnsplashFragment : BaseFragment<FragmentUnsplashBinding>(FragmentUnsplashBinding::inflate) {

    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var menuHost: MenuHost

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()

        setMenu()

    }

    private fun initUi() = with(binding) {
        viewPagerAdapter = ViewPagerAdapter(childFragmentManager, lifecycle, itemsTabLayout)
        viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = itemsTabLayout[position]
        }.attach()
    }

    private fun setMenu() {
        menuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                val id = menuItem.itemId
                if (id == R.id.search) {
                    findNavController().navigate(UnsplashFragmentDirections.toSearchFragment())
                }
                menuItem.isVisible = false
                return true
            }

            override fun onPrepareMenu(menu: Menu) {
                super.onPrepareMenu(menu)
                menu.clear()
                val menuInflater = activity?.menuInflater
                menuInflater?.inflate(R.menu.menu, menu)
            }
        })
    }

}