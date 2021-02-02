package com.phover.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.phover.databinding.ItemCollectionBinding
import com.phover.model.RoverPhoto
import com.phover.utility.PhotoClickListener

class CollectionAdapter(private val listener: PhotoClickListener) : PagingDataAdapter<RoverPhoto,
        CollectionAdapter.ViewHolder>(PHOTO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCollectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        getItem(position)?.let { item ->
            viewHolder.bind(item)
            viewHolder.itemView.setOnClickListener {
                listener.onClick(item)
            }
        }
    }

    class ViewHolder(private val binding: ItemCollectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RoverPhoto) {
            binding.item = item
            binding.executePendingBindings()
        }
    }

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<RoverPhoto>() {
            override fun areItemsTheSame(oldItem: RoverPhoto, newItem: RoverPhoto):
                    Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: RoverPhoto, newItem: RoverPhoto):
                    Boolean = oldItem == newItem
        }
    }
}