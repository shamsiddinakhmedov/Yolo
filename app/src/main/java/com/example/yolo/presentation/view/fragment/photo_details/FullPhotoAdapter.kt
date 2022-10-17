package com.example.yolo.presentation.view.fragment.photo_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yolo.databinding.ItemFullPhotoBinding
import com.example.yolo.presentation.view.fragment.photo_details.FullPhotoAdapter.ViewHolder

class FullPhotoAdapter : RecyclerView.Adapter<ViewHolder>() {

    inner class ViewHolder(itemFullPhotoBinding: ItemFullPhotoBinding) :
        RecyclerView.ViewHolder(itemFullPhotoBinding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemFullPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {}

    override fun getItemCount(): Int = 0
}