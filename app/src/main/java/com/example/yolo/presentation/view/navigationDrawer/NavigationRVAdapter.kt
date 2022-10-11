package com.example.yolo.presentation.view.navigationDrawer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yolo.databinding.RowNavDrawerBinding
import com.example.yolo.domain.model.NavigationItemModel

class NavigationRVAdapter(
    private var items: ArrayList<NavigationItemModel>
) : RecyclerView.Adapter<NavigationRVAdapter.NavigationItemViewHolder>() {

    inner class NavigationItemViewHolder(private val binding: RowNavDrawerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(position: Int) = with(binding) {
            navigationIcon.setImageResource(items[position].icon)
            navigationTitle.text = items[position].title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigationItemViewHolder {
        return NavigationItemViewHolder(
            RowNavDrawerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: NavigationItemViewHolder, position: Int) {
        holder.onBind(position)
    }
}