package com.example.yolo.presentation.view.fragment.folder

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.example.yolo.databinding.FragmentFolderBinding
import com.example.yolo.domain.model.unsplash.Photos
import com.example.yolo.presentation.architecture.BaseFragment
import com.example.yolo.presentation.view.fragment.photos.PhotoLoadStateAdapter
import com.example.yolo.presentation.view.fragment.photos.PhotosAdapter
import com.example.yolo.presentation.view.fragment.photos.PhotosFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FolderFragment : BaseFragment<FragmentFolderBinding>(FragmentFolderBinding::inflate) {

    private lateinit var adapter: PhotosAdapter
    private val viewModel by viewModels<PhotosFragmentViewModel>()
    private var query: String? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arg = arguments
        query = arg?.getString("query")

        initUi()

        viewModel.state.collect(this::renderPhotos) { it.photos }
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

        viewModel.searchPhotos(query!!)
    }

    private fun onClick(photos: Photos) {
        Toast.makeText(requireContext(), photos.createdAt, Toast.LENGTH_SHORT).show()
    }
}