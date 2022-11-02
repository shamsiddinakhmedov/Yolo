package com.example.yolo.presentation.view.fragment.photos

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.example.yolo.app.common.Constants
import com.example.yolo.app.common.Constants.ARG_QUERY
import com.example.yolo.app.common.Constants.LATEST
import com.example.yolo.app.common.Constants.ORDER_BY
import com.example.yolo.databinding.FragmentPhotosBinding
import com.example.yolo.domain.model.unsplash.Photos
import com.example.yolo.presentation.architecture.BaseFragment
import com.example.yolo.presentation.view.fragment.photos.PhotosViewModel.Input
import com.example.yolo.presentation.view.fragment.photos.PhotosViewModel.NetworkState
import com.example.yolo.presentation.view.fragment.unsplash_main.UnsplashFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotosFragment : BaseFragment<FragmentPhotosBinding>(FragmentPhotosBinding::inflate) {

    private var adapter = PhotosAdapter(this::onClick)
    private val viewModel by viewModels<PhotosViewModel>()

    private var argQuery: String = Constants.itemsTabLayout[1]
    private var argOrderBy: String = LATEST

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        viewModel.state.collect(this::renderPhotos) { it.photos }
        viewModel.state.collect(this::renderNetworkState) { it.networkState }
    }

    private fun initUi() = with(binding) {
        arguments.let {
            argQuery = it?.getString(ARG_QUERY).toString()
            argOrderBy = it?.getString(ORDER_BY).toString()
        }

        viewModel.processInput(Input.SetAttributes(argQuery, argOrderBy))
        viewModel.searchPhotos()

        rv.adapter = adapter
        rv.setHasFixedSize(true)
        adapter.addLoadStateListener { loadState ->
            viewModel.processInput(Input.LoadStates(loadState, adapter.itemCount))
        }

        retryBtn.setOnClickListener {
            viewModel.processInput(Input.Retry)
        }
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

    private fun renderNetworkState(networkState: NetworkState) = with(binding) {
        progressBar.isVisible = networkState is NetworkState.Loading
        noLoadedTv.isVisible = networkState is NetworkState.Error
        retryBtn.isVisible = networkState is NetworkState.Error
    }

    private fun onClick(photos: Photos) {
        findNavController().navigate(UnsplashFragmentDirections.toPhotoDetailsFragment(photos))
    }

    companion object {
        @JvmStatic
        fun sendData(query: String, order: String) =
            PhotosFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_QUERY, query)
                    putString(ORDER_BY, order)
                }
            }
    }
}