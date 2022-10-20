package com.example.yolo.presentation.view.fragment.unsplash_main

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
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
    private var popular: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        popular = arguments?.getString("popular")
        initUi()

    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        val info = menu.findItem(R.id.info)
        val share = menu.findItem(R.id.share)
        val search = menu.findItem(R.id.search)

        search.isVisible = true
        info.isVisible = false
        share.isVisible = false
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search -> {
                findNavController().navigate(R.id.searchFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initUi() = with(binding) {
        viewPagerAdapter =
            ViewPagerAdapter(childFragmentManager, lifecycle, itemsTabLayout, popular.toString())
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
                val info = menu.findItem(R.id.info)
                val share = menu.findItem(R.id.share)
                val search = menu.findItem(R.id.search)

                search.isVisible = true
                info.isVisible = false
                share.isVisible = false
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

                return when (menuItem.itemId) {
                    R.id.search -> {
                        Toast.makeText(requireContext(), "Click in search", Toast.LENGTH_SHORT)
                            .show()
                        true
                    }
                    else -> true
                }
            }
        })
    }

}