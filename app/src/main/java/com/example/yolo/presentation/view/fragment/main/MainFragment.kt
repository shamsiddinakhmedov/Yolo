package com.example.yolo.presentation.view.fragment.main

import android.os.Bundle
import android.view.View
import com.example.yolo.app.common.Constants.itemsTabLayout
import com.example.yolo.databinding.FragmentMainBinding
import com.example.yolo.presentation.architecture.BaseFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTabLayout()

        initUi()
    }

    private fun setTabLayout() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }

    private fun initUi() = with(binding) {
        viewPagerAdapter = ViewPagerAdapter(childFragmentManager, lifecycle, itemsTabLayout)
        viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = itemsTabLayout[position]
        }.attach()
    }
}