package com.example.yolo.presentation.view.fragment.photo_details

import android.app.DownloadManager
import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.*
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.example.yolo.R
import com.example.yolo.app.common.Constants
import com.example.yolo.databinding.FragmentPhotoDetailsBinding
import com.example.yolo.databinding.InfoBottomSheetBinding
import com.example.yolo.databinding.WallpaperBottomSheetBinding
import com.example.yolo.domain.model.unsplash.Photos
import com.example.yolo.presentation.architecture.BaseFragment
import com.example.yolo.presentation.view.fragment.photo_details.PhotoDetailsViewModel.Input.SetLikedPhoto
import com.example.yolo.presentation.view.fragment.photo_details.PhotoDetailsViewModel.Input.SetPhoto
import com.example.yolo.presentation.view.utils.snackBar
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.IOException

@AndroidEntryPoint
class PhotoDetailsFragment :
    BaseFragment<FragmentPhotoDetailsBinding>(FragmentPhotoDetailsBinding::inflate) {

    private val args by navArgs<PhotoDetailsFragmentArgs>()
    private lateinit var photo: Photos
    private lateinit var wallpaperManager: WallpaperManager
    private val viewModel by viewModels<PhotoDetailsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        wallpaperManager = WallpaperManager.getInstance(context)
        setHasOptionsMenu(true)

        photo = args.photo!!

        initUi()
        viewModel.processInput(SetPhoto(photo))
        viewModel.state.collect(this::renderPhoto) { it.photo }
    }

    private fun initUi() = with(binding) {

            download.setOnClickListener {
                downloadPhoto()
            }
            wallpaper.setOnClickListener {
                setWallpaper()
            }
            change.setOnClickListener {
                toChangePhoto()
            }
            like.setOnClickListener {
                putLike()
        }
    }

    private fun putLike() {
        viewModel.processInput(SetLikedPhoto)
    }

    private fun renderPhoto(photo: Photos?) = with(binding) {
        if (photo?.liked == true) {
            like.setBackgroundResource(R.drawable.ic_like_filled)
        } else {
            like.setBackgroundResource(R.drawable.ic_like_empty)
        }
        uploadPhoto(photo!!.urls.regular)
    }

    private fun uploadPhoto(url: String) = with(binding) {
        Glide.with(root).load(url)
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
            .into(fullPhoto)
    }

    private fun shareUrl() {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT, "${Constants.unsplash}photos/${photo.id}"
            )
            type = "text/plain"
        }
        startActivity(Intent.createChooser(intent, null))
    }

    private fun infoPhoto() {
        val dialog = BottomSheetDialog(requireContext())
        val view = InfoBottomSheetBinding.inflate(
            LayoutInflater.from(requireContext()),
            binding.root,
            false
        )
        view.apply {
            link.text = requireContext().getString(R.string.website, photo.urls.full)
            author.text = requireContext().getString(R.string.author, photo.user.name)
            description.text = photo.description
        }
        dialog.setContentView(view.root)
        dialog.show()
    }

    private fun downloadPhoto() {
        val filename = "${photo.id}.jpg"
        val downloadUrlOfImage = photo.links.download
        val direct = File(
            resources.getString(R.string.app_name) + "/"
        )

        if (!direct.exists()) {
            direct.mkdir()
        }

        val dm =
            requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadUri: Uri = Uri.parse(downloadUrlOfImage)
        val request = DownloadManager.Request(downloadUri)
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setAllowedOverRoaming(false)
            .setTitle(filename)
            .setMimeType("image/jpeg")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_PICTURES,
                File.separator + getString(R.string.app_name) + File.separator.toString() + filename
            )

        snackBar(R.string.saved)
        dm.enqueue(request)
    }

    private fun setWallpaper() {
        val dialog = BottomSheetDialog(requireContext())
        val view = WallpaperBottomSheetBinding.inflate(
            LayoutInflater.from(requireContext()),
            binding.root,
            false
        )
        view.apply {
            home.setOnClickListener {
                Glide.with(requireContext())
                    .asBitmap()
                    .load(photo.urls.full)
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            val wallpaperManager = WallpaperManager.getInstance(context)
                            try {
                                wallpaperManager.setBitmap(resource)
                            } catch (ex: IOException) {
                                ex.printStackTrace()
                            }
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {}
                    })
            }

            lock.setOnClickListener {
                Glide.with(requireContext())
                    .asBitmap()
                    .load(photo.urls.full)
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            val wallpaperManager = WallpaperManager.getInstance(context)
                            try {
                                wallpaperManager.setBitmap(
                                    resource,
                                    null,
                                    true,
                                    WallpaperManager.FLAG_LOCK
                                )
                            } catch (ex: IOException) {
                                ex.printStackTrace()
                            }
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {}
                    })
            }

            bothScreen.setOnClickListener {
                Glide.with(requireContext())
                    .asBitmap()
                    .load(photo.urls.full)
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            val wallpaperManager = WallpaperManager.getInstance(context)
                            try {
                                wallpaperManager.setBitmap(
                                    resource,
                                    null,
                                    true,
                                    WallpaperManager.FLAG_LOCK
                                )
                                wallpaperManager.setBitmap(resource)
                            } catch (ex: IOException) {
                                ex.printStackTrace()
                            }
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {}
                    })
            }
            dialog.setContentView(view.root)
            dialog.show()
        }
    }

    private fun toChangePhoto() {
        findNavController().navigate(PhotoDetailsFragmentDirections.toChangePhotoFragment(photo))
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        val search = menu.findItem(R.id.search)
        val info = menu.findItem(R.id.info)
        val share = menu.findItem(R.id.share)
        search.isVisible = false
        info.isVisible = true
        share.isVisible = true
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.info -> {
                infoPhoto()
                true
            }
            R.id.share -> {
                shareUrl()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}