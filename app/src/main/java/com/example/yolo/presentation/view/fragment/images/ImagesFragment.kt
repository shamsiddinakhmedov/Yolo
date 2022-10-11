package com.example.yolo.presentation.view.fragment.images

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.yolo.databinding.FragmentImagesBinding
import com.example.yolo.presentation.architecture.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImagesFragment : BaseFragment<FragmentImagesBinding>(FragmentImagesBinding::inflate) {

    private lateinit var adapter: ImagesAdapter
    private val viewModel by viewModels<ImagesFragmentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ImagesAdapter {
            Toast.makeText(requireContext(), "Click", Toast.LENGTH_SHORT).show()
        }

        binding.apply {
            rv.setHasFixedSize(true)
            rv.adapter = adapter
        }
        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }
}