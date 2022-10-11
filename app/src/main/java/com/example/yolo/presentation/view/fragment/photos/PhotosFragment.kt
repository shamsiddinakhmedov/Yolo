package com.example.yolo.presentation.view.fragment.photos

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.yolo.databinding.FragmentPhotosBinding
import com.example.yolo.domain.model.unsplash.Photos
import com.example.yolo.presentation.architecture.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotosFragment : BaseFragment<FragmentPhotosBinding>(FragmentPhotosBinding::inflate) {

    private lateinit var adapter: PhotosAdapter
    private val viewModel by viewModels<PhotosFragmentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PhotosAdapter(this::onClick)

        binding.apply {
            rv.setHasFixedSize(true)
            rv.adapter = adapter.withLoadStateHeaderAndFooter(
                header = PhotoLoadStateAdapter { adapter.retry() },
                footer = PhotoLoadStateAdapter { adapter.retry() }
            )
        }
        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    private fun onClick(photos: Photos) {
        Toast.makeText(requireContext(), photos.createdAt, Toast.LENGTH_SHORT).show()
    }
}