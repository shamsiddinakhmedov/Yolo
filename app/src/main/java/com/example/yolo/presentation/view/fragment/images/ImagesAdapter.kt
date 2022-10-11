package com.example.yolo.presentation.view.fragment.images

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.yolo.R
import com.example.yolo.databinding.ImageItemBinding
import com.example.yolo.domain.model.unsplash.Photos
import com.example.yolo.domain.model.unsplash.Urls

class ImagesAdapter(val onclick: (Photos) -> Unit) :
    PagingDataAdapter<Photos, ImagesAdapter.ViewHolder>(DIFF_UTIL) {

    inner class ViewHolder(private val binding: ImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(urls: Urls) = with(binding) {
            Glide.with(root.context)
                .load(urls.small)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.error)
                .into(image)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position)?.urls ?: return)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Photos>() {

            override fun areItemsTheSame(oldItem: Photos, newItem: Photos) = oldItem == newItem

            override fun areContentsTheSame(oldItem: Photos, newItem: Photos) = oldItem == newItem
        }
    }
}