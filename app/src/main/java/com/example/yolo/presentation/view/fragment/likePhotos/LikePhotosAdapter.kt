package com.example.yolo.presentation.view.fragment.likePhotos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.yolo.R
import com.example.yolo.data.database.LikedPhotoDB
import com.example.yolo.databinding.PhotoItemBinding

class LikePhotosAdapter(val onClick: (LikedPhotoDB) -> Unit) :
    ListAdapter<LikedPhotoDB, LikePhotosAdapter.ViewHolder>(DIFF_UTIL) {

    inner class ViewHolder(private val binding: PhotoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(photos: LikedPhotoDB) = with(binding) {
            Glide.with(root.context)
                .load(photos.photos.urls.small)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_error)
                .into(image)

            root.setOnClickListener {
                onClick(photos)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        PhotoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<LikedPhotoDB>() {
            override fun areItemsTheSame(oldItem: LikedPhotoDB, newItem: LikedPhotoDB) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: LikedPhotoDB, newItem: LikedPhotoDB) =
                oldItem == newItem
        }
    }
}