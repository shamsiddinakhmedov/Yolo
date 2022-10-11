package com.example.yolo.presentation.view.fragment.main

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.example.yolo.R
import com.example.yolo.databinding.FragmentMainBinding
import com.example.yolo.domain.model.NavigationItemModel
import com.example.yolo.presentation.architecture.BaseFragment
import com.example.yolo.presentation.view.navigationDrawer.NavigationRVAdapter
import com.example.yolo.presentation.view.navigationDrawer.RecyclerTouchListener
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationRVAdapter: NavigationRVAdapter
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var menuHost: MenuHost

    private val viewModel by viewModels<MainFragmentViewModel>()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        drawerLayout = requireView().findViewById(R.id.drawer_layout)

        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.activityMainToolbar)

        setNavigationDrawer()

        setTabLayout()

        initUi()

        menuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)
                val searchItem = menu.findItem(R.id.search)
                val searchView = searchItem.actionView as SearchView
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        if (query != null) {
                            viewModel
                        }
                        return true
                    }

                    override fun onQueryTextChange(p0: String?): Boolean = true
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setNavigationDrawer() = with(binding) {
        navigationRv.setHasFixedSize(true)
        navigationRVAdapter = NavigationRVAdapter(itemsNavigation)
        navigationRv.adapter = navigationRVAdapter
        navigationRv.addOnItemTouchListener(RecyclerTouchListener(requireContext(), object :
            RecyclerTouchListener.ClickListener {
            override fun onClick(view: View, position: Int) {
                when (position) {
                    0 -> {
                        Toast.makeText(requireContext(), "0", Toast.LENGTH_SHORT).show()
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
                Handler().postDelayed({
                    drawerLayout.closeDrawer(GravityCompat.START)
                }, 200)
            }
        }))

        val toggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            requireActivity(),
            drawerLayout,
            activityMainToolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ) {
            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                try {
                    val inputMethodManager =
                        activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(
                        activity!!.currentFocus!!.windowToken,
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
                        activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(
                        activity!!.currentFocus!!.windowToken,
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

    private fun setTabLayout() = with(binding) {
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
        viewPagerAdapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = itemsTabLayout[position]
        }.attach()
    }
}