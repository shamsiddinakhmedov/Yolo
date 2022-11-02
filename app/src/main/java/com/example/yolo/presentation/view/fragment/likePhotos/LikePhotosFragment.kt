package com.example.yolo.presentation.view.fragment.likePhotos

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.yolo.data.database.LikedPhotoDB
import com.example.yolo.databinding.FragmentLikePhotosBinding
import com.example.yolo.domain.model.unsplash.Photos
import com.example.yolo.presentation.architecture.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LikePhotosFragment :
    BaseFragment<FragmentLikePhotosBinding>(FragmentLikePhotosBinding::inflate) {

    private val adapter = LikePhotosAdapter(this::onClick)
    private val viewModel by viewModels<LikePhotosViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        viewModel.state.collect(this::renderPhotos) { it.likedPhotosList }
    }

    private fun initUi() = with(binding) {
        rv.adapter = adapter

    }

    private fun renderPhotos(photos: List<LikedPhotoDB>?) {
        adapter.submitList(photos)
    }

    private fun onClick(photos: LikedPhotoDB) {
        val photo = Photos(
            photos.photos.id,
            photos.photos.description,
            photos.photos.color,
            photos.photos.urls,
            photos.photos.user,
            photos.photos.links,
            photos.photos.liked
        )
        findNavController().navigate(LikePhotosFragmentDirections.toPhotoDetailsFragment(photo))
    }
}
