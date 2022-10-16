package com.example.yolo.app.common

import com.example.yolo.R
import com.example.yolo.domain.model.NavigationItemModel

object Constants {

    const val unsplash = "https://unsplash.com/"
    const val BASE_URL = "https://api.unsplash.com/"
    const val KEY = "prMrqL_0Ba9E53tjEgfYOxSSWlS9AZasI-n7W0iY46o"

    val itemsNavigation = arrayListOf(
        NavigationItemModel(R.drawable.home, "Home"),
        NavigationItemModel(R.drawable.ic_popular, "Popular"),
        NavigationItemModel(R.drawable.ic_random, "Random"),
        NavigationItemModel(R.drawable.ic_like, "Like"),
        NavigationItemModel(R.drawable.ic_history, "History"),
        NavigationItemModel(R.drawable.ic_about, "About"),
    )

    val itemsTabLayout = arrayListOf(
        "All", "New", "Animals", "Technology", "Nature"
    )

}