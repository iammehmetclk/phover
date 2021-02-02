package com.phover.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.phover.R

class NetworkStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<NetworkStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        LoadStateViewHolder(parent, retry)

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) =
        holder.bind(loadState)

    class LoadStateViewHolder(
        parent: ViewGroup,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.load_state_item, parent, false)
    ) {
        private val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar)
        private val textView: TextView = itemView.findViewById(R.id.text_view)
        private val button: Button = itemView.findViewById(R.id.button)

        init {
            button.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            progressBar.isVisible = loadState is LoadState.Loading
            progressBar.isVisible = loadState is LoadState.Loading
            button.isVisible = loadState !is LoadState.Loading
            textView.isVisible = loadState !is LoadState.Loading
        }
    }
}