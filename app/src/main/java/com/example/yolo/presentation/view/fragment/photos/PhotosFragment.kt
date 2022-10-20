package com.example.yolo.presentation.view.fragment.photos

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.example.yolo.app.common.Constants
import com.example.yolo.databinding.FragmentPhotosBinding
import com.example.yolo.domain.model.unsplash.Photos
import com.example.yolo.presentation.architecture.BaseFragment
import com.example.yolo.presentation.view.fragment.unsplash_main.UnsplashFragmentDirections
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PhotosFragment : BaseFragment<FragmentPhotosBinding>(FragmentPhotosBinding::inflate) {

    private lateinit var adapter: PhotosAdapter
    private val viewModel by viewModels<PhotosViewModel>()
    private var query: String = Constants.itemsTabLayout[1]
    private val argQuery = "query"
    private var popular: String = ""

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
            rv.adapter = adapter
        }

        arguments.let {
            query = if (it != null) {
                it.getString(argQuery)!!
            } else {
                Constants.itemsTabLayout[1]
            }
            popular = it?.getString("popular").toString()
        }

        viewModel.searchPhotos(query)
    }

    private fun onClick(photos: Photos) {
        findNavController().navigate(UnsplashFragmentDirections.toPhotoDetailsFragment(photos))
    }

    companion object {
        @JvmStatic
        fun sendData(query: String, popularString: String) =
            PhotosFragment().apply {
                arguments = Bundle().apply {
                    putString(argQuery, query)
                    putString(popular, popularString)
                }
            }
    }
}