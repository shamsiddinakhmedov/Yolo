package com.example.yolo.presentation.view.fragment.photos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.yolo.databinding.PhotoLoadStateFooterBinding

class PhotoLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<PhotoLoadStateAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: PhotoLoadStateFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.retryBtn.setOnClickListener {
                retry.invoke()
            }
        }
        fun onBind(loadState: LoadState) {
            binding.apply {
                progress.isVisible = loadState is LoadState.Loading
                retryBtn.isVisible = loadState !is LoadState.Loading
                noLoadedTv.isVisible = loadState is LoadState.Error
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.onBind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder =
        ViewHolder(
            PhotoLoadStateFooterBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )
}