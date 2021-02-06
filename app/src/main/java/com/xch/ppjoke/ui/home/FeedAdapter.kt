package com.xch.ppjoke.ui.home

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.xch.ppjoke.model.Feed

class FeedAdapter : PagedListAdapter<Feed, FeedAdapter.ViewHolder> {

    protected constructor():super(object : DiffUtil.ItemCallback<Feed>() {
        override fun areItemsTheSame(oldItem: Feed, newItem: Feed): Boolean {
            return oldItem.itemId == newItem.itemId
        }

        override fun areContentsTheSame(oldItem: Feed, newItem: Feed): Boolean {
            TODO("Not yet implemented")
        }
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}