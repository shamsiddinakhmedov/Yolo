package com.example.yolo.presentation.view.fragment.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.paging.PagingData
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
    private val args by navArgs<SearchFragmentArgs>()
    lateinit var query: String
    lateinit var searchView: SearchView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        query = args.query ?: "All"
        Toast.makeText(requireContext(), query, Toast.LENGTH_SHORT).show()
        viewModel.searchPhotos(query)

        initUi()

//        renderSearchView()

        viewModel.state.collect(this::renderPhotos) { it.photos }
    }

    private fun renderSearchView() {
        searchView = SearchView(requireContext())
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(requireContext(), query, Toast.LENGTH_SHORT).show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = true
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
    }

    private fun onClick(photos: Photos) {
        Toast.makeText(requireContext(), photos.description ?: "", Toast.LENGTH_SHORT).show()
    }
}