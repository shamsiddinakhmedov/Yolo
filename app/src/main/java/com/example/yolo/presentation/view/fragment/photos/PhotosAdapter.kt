package com.example.yolo.presentation.view.fragment.photos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.yolo.R
import com.example.yolo.databinding.PhotoItemBinding
import com.example.yolo.domain.model.unsplash.Photos

class PhotosAdapter(val onClick: (Photos) -> Unit) :
    PagingDataAdapter<Photos, PhotosAdapter.ViewHolder>(DIFF_UTIL) {

    inner class ViewHolder(private val binding: PhotoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(photos: Photos) = with(binding) {
            Glide.with(root.context)
                .load(photos.urls.small)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_error)
                .into(image)

            root.setOnClickListener {
                onClick(photos)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position) ?: return)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        PhotoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Photos>() {

            override fun areItemsTheSame(oldItem: Photos, newItem: Photos) = oldItem == newItem

            override fun areContentsTheSame(oldItem: Photos, newItem: Photos) = oldItem == newItem
        }
    }
}