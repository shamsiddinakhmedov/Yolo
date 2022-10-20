package com.example.yolo.presentation.view.fragment.search

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.example.yolo.R
import com.example.yolo.databinding.FragmentSearchBinding
import com.example.yolo.domain.model.unsplash.Photos
import com.example.yolo.presentation.architecture.BaseFragment
import com.example.yolo.presentation.view.fragment.photos.PhotoLoadStateAdapter
import com.example.yolo.presentation.view.fragment.photos.PhotosAdapter
import com.example.yolo.presentation.view.fragment.photos.PhotosViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private lateinit var adapter: PhotosAdapter
    private val viewModel by viewModels<PhotosViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        initUi()

        viewModel.state.collect(this::renderPhotos) { it.photos }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        val info = menu.findItem(R.id.info)
        val share = menu.findItem(R.id.share)
        val search = menu.findItem(R.id.search)
        search.actionView = SearchView(requireContext())
        search.expandActionView()

        search.isVisible = true
        info.isVisible = false
        share.isVisible = false

        renderSearchView(search)

        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun renderSearchView(search: MenuItem) {
        val searchView: SearchView = search.actionView as SearchView

        //text
        val editText: EditText =
            searchView.findViewById<View>(androidx.appcompat.R.id.search_src_text) as EditText
        editText.setTextColor(Color.WHITE)
        editText.setHintTextColor(Color.WHITE)

        //clear icon
        val searchClose: ImageView =
            searchView.findViewById(androidx.appcompat.R.id.search_close_btn)
        searchClose.setColorFilter(Color.WHITE)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.searchPhotos(query)
                }
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = true
        })

        search.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean = true

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                findNavController().navigate(R.id.unsplashFragment)
                return true
            }
        })
    }

    private fun renderPhotos(photos: PagingData<Photos>?) {
        if (photos == null) {
            adapter.submitData(lifecycle, PagingData.empty())
            return
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            adapter.submitData(photos)
        }
    }

    private fun initUi() {
        adapter = PhotosAdapter(this::onClick)

        binding.apply {
            rv.setHasFixedSize(true)
            rv.adapter = adapter.withLoadStateHeaderAndFooter(
                header = PhotoLoadStateAdapter { adapter.retry() },
                footer = PhotoLoadStateAdapter { adapter.retry() }
            )
        }
        adapter.addLoadStateListener { loadState ->
           binding.apply {
           }
        }
    }

    private fun onClick(photos: Photos) {
        findNavController().navigate(SearchFragmentDirections.toPhotoDetailsFragment(photos))
    }
}