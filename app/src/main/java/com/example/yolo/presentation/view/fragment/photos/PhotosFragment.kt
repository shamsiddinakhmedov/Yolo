package com.example.yolo.presentation.view.fragment.photos

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.example.yolo.databinding.FragmentPhotosBinding
import com.example.yolo.domain.model.unsplash.Photos
import com.example.yolo.presentation.architecture.BaseFragment
import com.example.yolo.presentation.view.fragment.unsplash_main.UnsplashFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

private const val arg_query = "query"

@AndroidEntryPoint
class PhotosFragment : BaseFragment<FragmentPhotosBinding>(FragmentPhotosBinding::inflate) {

    private lateinit var adapter: PhotosAdapter
    private val viewModel by viewModels<PhotosViewModel>()
    private var query: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        arguments?.let {
            query = it.getString(arg_query)
        }

        viewModel.searchPhotos(query!!)
    }

    private fun onClick(photos: Photos) {
        findNavController().navigate(UnsplashFragmentDirections.toPhotoFragment(photos))
    }

    companion object {
        @JvmStatic
        fun sendData(query: String) =
            PhotosFragment().apply {
                arguments = Bundle().apply {
                    putString(arg_query, query)
                }
            }
    }
}