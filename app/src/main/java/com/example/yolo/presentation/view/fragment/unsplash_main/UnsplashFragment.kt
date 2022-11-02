package com.example.yolo.presentation.view.fragment.unsplash_main

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.yolo.R
import com.example.yolo.app.common.Constants.LATEST
import com.example.yolo.app.common.Constants.ORDER_BY
import com.example.yolo.app.common.Constants.POPULAR
import com.example.yolo.app.common.Constants.itemsNavigation
import com.example.yolo.app.common.Constants.itemsTabLayout
import com.example.yolo.databinding.FragmentUnsplashBinding
import com.example.yolo.presentation.architecture.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UnsplashFragment : BaseFragment<FragmentUnsplashBinding>(FragmentUnsplashBinding::inflate) {

    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private var orderBy: String = LATEST

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        orderBy = arguments?.getString(ORDER_BY) ?: LATEST

        if (orderBy == POPULAR) {
            activity?.title = itemsNavigation[1].title
        } else {
            activity?.title = itemsNavigation[0].title
        }
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
            ViewPagerAdapter(childFragmentManager, lifecycle, itemsTabLayout, orderBy)
        viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = itemsTabLayout[position]
        }.attach()
    }
}