package com.example.yolo.presentation.view.fragment.photo_details

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.yolo.R
import com.example.yolo.app.common.Constants
import com.example.yolo.databinding.BottomSheetDialogBinding
import com.example.yolo.databinding.FragmentPhotoDetailsBinding
import com.example.yolo.domain.model.unsplash.Photos
import com.example.yolo.presentation.architecture.BaseFragment
import com.google.android.material.bottomsheet.BottomSheetDialog

class PhotoDetailsFragment :
    BaseFragment<FragmentPhotoDetailsBinding>(FragmentPhotoDetailsBinding::inflate) {

    private val args by navArgs<PhotoDetailsFragmentArgs>()
    private lateinit var photo: Photos

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        if (args.photo != null) {
            photo = args.photo!!
        }
        initUi()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }

    @SuppressLint("InflateParams")
    private fun initUi() = with(binding) {
        //upload photo
        Glide.with(binding.root).load(photo.urls.full)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    progress.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    progress.visibility = View.GONE
                    return false
                }
            })
            .centerCrop()
            .into(binding.fullPhoto)

        binding.apply {
            back.setOnClickListener {
                findNavController().popBackStack()
            }
            share.setOnClickListener {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT, "${Constants.unsplash}${photo.id}"
                    )
                    type = "text/plain"
                }
                startActivity(Intent.createChooser(intent, null))
            }

            info.setOnClickListener {
                val dialog = BottomSheetDialog(requireContext())
                val view = BottomSheetDialogBinding.inflate(
                    LayoutInflater.from(requireContext()),
                    root,
                    false
                )
                view.apply {
                    link.text = "${R.string.website}: ${photo.urls.full}"
                    author.text = ""
                }
                dialog.setContentView(view.root)
                dialog.show()
            }
        }

    }
}